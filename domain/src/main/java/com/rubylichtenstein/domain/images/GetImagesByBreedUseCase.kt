package com.rubylichtenstein.domain.images

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.favorites.BreedImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesByBreedUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {
    operator fun invoke(breed: String): Flow<AsyncResult<List<BreedImage>>> {
        return imagesRepository.getImagesByBreed(breed)
    }
}