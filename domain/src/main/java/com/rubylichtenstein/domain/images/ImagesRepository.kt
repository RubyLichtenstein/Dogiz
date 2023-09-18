package com.rubylichtenstein.domain.images

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.favorites.BreedImage
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImagesByBreed(breed: String): Flow<AsyncResult<List<BreedImage>>>
}