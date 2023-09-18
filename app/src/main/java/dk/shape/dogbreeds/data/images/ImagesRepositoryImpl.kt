package dk.shape.dogbreeds.data.images

import dk.shape.domain.common.AsyncResult
import dk.shape.domain.common.asResult
import dk.shape.domain.favorites.BreedImage
import dk.shape.domain.images.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepositoryImpl @Inject constructor(
    private val dogBreedApiService: BreedImagesApi,
    private val imagesDataStore: ImagesDataStore
) : ImagesRepository {
    override fun getImagesByBreed(breed: String): Flow<AsyncResult<List<BreedImage>>> = flow {
        val localImages = getImagesByBreedFromLocal(breed)
        if (localImages != null) {
            emit(localImages)
        }

        try {
            val remoteImages = dogBreedApiService.getBreedImages(breed).getOrThrow()
            val images = remoteImages.map { imageUrl -> BreedImage(imageUrl, breed) }
            imagesDataStore.save(images, breed)
            emit(images)
        } catch (e: Exception) {
            val fallbackLocalImages = getImagesByBreedFromLocal(breed)
            if (fallbackLocalImages != null) {
                emit(fallbackLocalImages)
            } else {
                throw e
            }
        }
    }.asResult()

    private suspend fun getImagesByBreedFromLocal(breed: String): List<BreedImage>? {
        return imagesDataStore.getByBreed(breed).first()
    }
}