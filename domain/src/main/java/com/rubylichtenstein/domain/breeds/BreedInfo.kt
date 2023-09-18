package com.rubylichtenstein.domain.breeds

import kotlinx.serialization.Serializable


@Serializable
data class BreedInfo(
    val breed: String,
    val subBreeds: List<String>
) {
    companion object {
        fun fromMap(map: Map<String, List<String>>): List<BreedInfo> {
            return map.map { (breed, subBreeds) ->
                BreedInfo(breed, subBreeds)
            }
        }
    }
}
