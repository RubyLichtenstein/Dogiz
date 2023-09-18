package com.rubylichtenstein.domain.breeds

import com.rubylichtenstein.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface BreedsRepository {
    val breedsFlow: Flow<AsyncResult<List<BreedInfo>>>
}