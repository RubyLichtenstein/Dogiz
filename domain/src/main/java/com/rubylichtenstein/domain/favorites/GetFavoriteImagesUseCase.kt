package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<AsyncResult<List<DogImageEntity>>> =
        favoritesRepository.favoriteImagesFlow
}

