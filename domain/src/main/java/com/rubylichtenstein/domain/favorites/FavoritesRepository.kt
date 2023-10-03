package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.images.DogImage
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteImagesFlow: Flow<List<DogImage>>

    suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean)
}