package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.images.DogImageData
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteImagesFlow: Flow<AsyncResult<List<DogImageData>>>

    suspend fun toggleFavorite(breedImage: DogImageData): Result<Unit>
}