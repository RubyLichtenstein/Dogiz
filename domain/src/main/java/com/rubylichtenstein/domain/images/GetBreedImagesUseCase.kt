package com.rubylichtenstein.domain.images

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.favorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetBreedImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(breedId: String): Flow<AsyncResult<List<DogImageEntity>>> {
        return imagesRepository.getImagesByBreed(breedId)
            .combine(favoritesRepository.favoriteImagesFlow) { breedImagesResult, favoritesResult ->
                when {
                    breedImagesResult is AsyncResult.Success && favoritesResult is AsyncResult.Success -> {
                        val favoriteImageUrls = favoritesResult.data.map { it.url }.toSet()
                        val breedEntities = breedImagesResult.data.map { breedImage ->
                            DogImageEntity(
                                breedImage.breedName,
                                favoriteImageUrls.contains(breedImage.url),
                                breedImage.url
                            )
                        }
                        AsyncResult.Success(breedEntities)
                    }

                    breedImagesResult is AsyncResult.Error -> breedImagesResult
                    favoritesResult is AsyncResult.Error -> favoritesResult
                    else -> AsyncResult.Error(Exception("Unknown error"))
                }
            }
    }
}
