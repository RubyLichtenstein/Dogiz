package com.rubylichtenstein.data.breeds

import com.rubylichtenstein.domain.breeds.data.BreedInfo
import kotlinx.serialization.Serializable


@Serializable
data class BreedInfoImpl(
    override val name: String,
    override val subBreedsNames: List<String>
) : BreedInfo {
    companion object {
        fun fromMap(map: Map<String, List<String>>): List<BreedInfoImpl> {
            return map.map { (breed, subBreeds) ->
                BreedInfoImpl(breed, subBreeds)
            }
        }
    }
}
