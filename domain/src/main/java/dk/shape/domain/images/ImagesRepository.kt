package dk.shape.domain.images

import dk.shape.domain.common.AsyncResult
import dk.shape.domain.favorites.BreedImage
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImagesByBreed(breed: String): Flow<AsyncResult<List<BreedImage>>>
}