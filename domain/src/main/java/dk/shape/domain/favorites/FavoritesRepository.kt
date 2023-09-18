package dk.shape.domain.favorites

import dk.shape.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteImagesFlow: Flow<AsyncResult<List<BreedImage>>>

    suspend fun toggleFavorite(breedImage: BreedImage): Result<Unit>
}