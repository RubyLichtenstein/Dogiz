package com.rubylichtenstein.data.favorites

import com.rubylichtenstein.data.images.DogImageDao
import com.rubylichtenstein.data.images.DogImageDataEntity.Companion.toDogImageEntity
import com.rubylichtenstein.domain.favorites.FavoritesRepository
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val imagesDao: DogImageDao
) : FavoritesRepository {

    override val favoriteImagesFlow: Flow<List<DogImageEntity>> =
        imagesDao.getFavoriteDogImages().map { it.map { it.toDogImageEntity() } }

    override suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            imagesDao.updateFavoriteStatus(
                url = url,
                isFavorite = isFavorite
            )
        }
    }
}


