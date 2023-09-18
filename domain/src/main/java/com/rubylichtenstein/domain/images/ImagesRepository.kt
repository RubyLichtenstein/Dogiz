package com.rubylichtenstein.domain.images

import com.rubylichtenstein.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface DogImageData {
    val breedName: String
    val url: String
}

interface ImagesRepository {
    fun getImagesByBreed(breed: String): Flow<AsyncResult<List<DogImageData>>>
}