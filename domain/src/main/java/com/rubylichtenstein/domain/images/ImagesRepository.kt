package com.rubylichtenstein.domain.images

import com.rubylichtenstein.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImagesByBreed(breed: String): Flow<AsyncResult<List<DogImageEntity>>>
}