package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asAsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeFavoritesRepository(
    initialDogImages: List<DogImageEntity>
) : FavoritesRepository {

    private val _images = MutableStateFlow(initialDogImages)

    override val favoriteImagesFlow: Flow<AsyncResult<List<DogImageEntity>>> =
        _images.map { images ->
            images.filter { it.isFavorite }
        }.asAsyncResult()

    override suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        val currentImages = _images.value.toMutableList()

        // Find the image with the given URL
        val imageIndex = currentImages.indexOfFirst { it.url == url }

        if (imageIndex != -1) {
            // Update the isFavorite status of the image
            val updatedImage = currentImages[imageIndex].copy(isFavorite = isFavorite)
            currentImages[imageIndex] = updatedImage
        }

        _images.value = currentImages
    }
}
