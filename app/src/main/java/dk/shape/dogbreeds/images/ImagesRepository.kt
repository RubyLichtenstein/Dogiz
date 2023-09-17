package dk.shape.dogbreeds.images

import dk.shape.dogbreeds.api.ApiResponse
import dk.shape.dogbreeds.api.BreedsApi
import dk.shape.dogbreeds.common.AsyncState
import dk.shape.dogbreeds.model.BreedImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepository @Inject constructor(
    private val dogBreedApiService: BreedsApi,
    private val imagesDataStore: ImagesDataStore
) {
    private val _breedImagesState = MutableStateFlow<AsyncState<List<BreedImage>>>(AsyncState.Loading)
    val breedImagesState: StateFlow<AsyncState<List<BreedImage>>> get() = _breedImagesState

    fun getImagesByBreed(breed: String, scope: CoroutineScope) {
        _breedImagesState.value = AsyncState.Loading

        scope.launch(Dispatchers.IO) {
            val localImages = getImagesByBreedFromLocal(breed)

            if (localImages != null) {
                _breedImagesState.value = AsyncState.Success(localImages)
            }

            fetchRemoteAndSaveToLocal(breed)
        }
    }

    private suspend fun fetchRemoteAndSaveToLocal(breed: String) {
        when (val imagesResponse = dogBreedApiService.getBreedImages(breed)) {
            is ApiResponse.Success -> {
                val images = imagesResponse.data.map { imageUrl ->
                    BreedImage(imageUrl, breed)
                }
                imagesDataStore.save(images, breed)
                _breedImagesState.value = AsyncState.Success(images)
            }

            is ApiResponse.Error -> {
                val localImages = getImagesByBreedFromLocal(breed)

                if (localImages != null) {
                    _breedImagesState.value = AsyncState.Success(localImages)
                } else {
                    _breedImagesState.value = AsyncState.Error(imagesResponse.message)
                }
            }
        }
    }

    private suspend fun getImagesByBreedFromLocal(breed: String): List<BreedImage>? {
        return imagesDataStore.getByBreed(breed).first()
    }

    fun resetState() {
        _breedImagesState.value = AsyncState.Loading
    }
}