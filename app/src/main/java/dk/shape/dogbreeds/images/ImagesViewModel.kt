package dk.shape.dogbreeds.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.shape.domain.common.AsyncResult
import dk.shape.domain.favorites.BreedImage
import dk.shape.domain.images.GetImagesByBreedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesByBreedUseCase: GetImagesByBreedUseCase,
) : ViewModel() {

    private val _dogImagesState =
        MutableStateFlow<AsyncResult<List<BreedImage>>>(AsyncResult.Loading)
    val dogImagesState: StateFlow<AsyncResult<List<BreedImage>>> get() = _dogImagesState

    fun fetchDogImages(breed: String, subBreed: String?) {
        val breedKey = if (subBreed == null) breed else "$breed/$subBreed"

        viewModelScope.launch {
            getImagesByBreedUseCase(breedKey)
                .collect { state -> _dogImagesState.value = state }
        }
    }
}

