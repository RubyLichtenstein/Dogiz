package com.rubylichtenstein.domain.breeds

import kotlinx.coroutines.flow.Flow

interface BreedsDataSource {
    val get: Flow<List<BreedInfo>?>
    suspend fun save(breeds: List<BreedInfo>)
}

interface BreedsApiSource {
    suspend fun getAllBreeds(): Result<Map<String, List<String>>>
}