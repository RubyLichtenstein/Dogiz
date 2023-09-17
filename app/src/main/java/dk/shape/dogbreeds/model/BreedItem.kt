package dk.shape.dogbreeds.model

import dk.shape.dogbreeds.common.capitalizeWords
import kotlinx.serialization.Serializable

@Serializable
sealed class BreedItem {
    abstract val name: String

    @Serializable
    data class Breed(
        override val name: String,
        val subBreeds: List<SubBreed>
    ) : BreedItem() {
        override fun breedName() = name.capitalizeWords()
    }

    @Serializable
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