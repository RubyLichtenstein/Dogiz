package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteImagesFlow: Flow<AsyncResult<List<BreedImage>>>

    suspend fun toggleFavorite(breedImage: BreedImage): Result<Unit>
}