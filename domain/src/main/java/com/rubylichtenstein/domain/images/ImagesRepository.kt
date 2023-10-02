package com.rubylichtenstein.domain.images

import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImagesByBreed(breedKey: String): Flow<List<DogImageEntity>>
}