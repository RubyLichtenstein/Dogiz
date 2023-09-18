package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.mapSuccess
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<AsyncResult<List<DogImageEntity>>> {
        return favoritesRepository.favoriteImagesFlow
            .map {
                it.mapSuccess { imageInfos ->
                    imageInfos.map { imageInfo ->
                        DogImageEntity(
                            imageInfo.breedName,
                            true,
                            imageInfo.url
                        )
                    }
                }
            }
    }
}

