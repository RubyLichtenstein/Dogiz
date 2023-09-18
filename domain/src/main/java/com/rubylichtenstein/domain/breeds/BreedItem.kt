package com.rubylichtenstein.domain.breeds

import com.rubylichtenstein.domain.common.capitalizeWords

sealed class BreedItem {
    abstract val name: String

    data class Breed(
        override val name: String,
        val subBreeds: List<SubBreed>
    ) : BreedItem() {
        override fun breedName() = name.capitalizeWords()
    }

    data class SubBreed(
        override val name: String,
        val parentBreed: String
    ) : BreedItem() {
        override fun breedName() = buildDisplayName(parentBreed, name)
    }

    abstract fun breedName(): String

    companion object {
        fun buildDisplayName(breed: String, subBreed: String?): String {
            val capitalizedBreed = breed.capitalizeWords()
            val capitalizedSubBreed = subBreed?.capitalizeWords()

            return if (capitalizedSubBreed != null) {
                "$capitalizedBreed ($capitalizedSubBreed)"
            } else {
                capitalizedBreed
            }
        }
    }
}