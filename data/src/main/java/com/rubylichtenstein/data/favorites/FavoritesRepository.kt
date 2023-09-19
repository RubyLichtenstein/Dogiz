package com.rubylichtenstein.data.favorites

import com.rubylichtenstein.data.images.DogImageDao
import com.rubylichtenstein.data.images.DogImageDataEntity.Companion.toDogImageEntity
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asAsyncResult
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

    override val favoriteImagesFlow: Flow<AsyncResult<List<DogImageEntity>>> =
        imagesDao.getFavoriteDogImages().map { it.map { it.toDogImageEntity() } }.asAsyncResult()

    override suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        //todo notify user in case of error
        withContext(Dispatchers.IO) {
            val ret = imagesDao.updateFavoriteStatus(
                url = url,
                isFavorite = isFavorite
            )
        }
    }
}


