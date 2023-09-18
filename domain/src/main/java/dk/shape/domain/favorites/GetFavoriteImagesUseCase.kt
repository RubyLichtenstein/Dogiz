package dk.shape.domain.favorites

import dk.shape.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<AsyncResult<List<BreedImage>>> {
        return favoritesRepository.favoriteImagesFlow
    }
}

class ToggleFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(breedImage: BreedImage) {
        favoritesRepository.toggleFavorite(breedImage)
    }
}