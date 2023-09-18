package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.common.capitalizeWords
import com.rubylichtenstein.domain.breeds.BreedItem.Companion.buildDisplayName
import kotlinx.serialization.Serializable

/**
 * Represents an image of a dog along with information about its breed or sub-breed.
 *
 * @param imageUrl The URL of the dog image.
 * @param breedKey A string representing either a breed or sub-breed of the dog.
 *                 The format is either "breed" for main breeds or "breed/subBreed" for sub-breeds.
 *                 For example, for a sub-breed like "Australian Shepherd," you would use "shepherd/australian."
 *                 For a main breed like "bulldog," just use "bulldog."
 */
@Serializable
data class BreedImage(
    val imageUrl: String,
    val breedKey: String
) {

    /**
     * Generates a display name for the breed or sub-breed.
     *
     * @return A string containing the capitalized breed and optionally the sub-breed.
     */
    fun displayName(): String {
        val parts = breedKey.split('/')
        val breed = parts[0].capitalizeWords()
        val subBreed = parts.getOrNull(1)?.capitalizeWords()

        return buildDisplayName(breed, subBreed)
    }
}
