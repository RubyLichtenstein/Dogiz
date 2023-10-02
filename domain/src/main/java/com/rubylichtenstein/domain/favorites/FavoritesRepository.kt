package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteImagesFlow: Flow<List<DogImageEntity>>

    suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean)
}