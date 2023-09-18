package dk.shape.domain.images

import dk.shape.domain.common.AsyncResult
import dk.shape.domain.favorites.BreedImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesByBreedUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {
    operator fun invoke(breed: String): Flow<AsyncResult<List<BreedImage>>> {
        return imagesRepository.getImagesByBreed(breed)
    }
}