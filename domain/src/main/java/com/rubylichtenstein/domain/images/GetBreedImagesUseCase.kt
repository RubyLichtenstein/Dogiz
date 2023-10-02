package com.rubylichtenstein.domain.images

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetBreedImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {
    operator fun invoke(breedKey: String): Flow<List<DogImageEntity>> {
        return imagesRepository.getImagesByBreed(breedKey).distinctUntilChanged()
    }
}
