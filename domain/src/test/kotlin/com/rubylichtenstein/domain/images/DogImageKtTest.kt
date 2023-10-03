package com.rubylichtenstein.domain.images

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DogImageKtTest {

    @Test
    fun `when subBreed is null, only breed should be returned`() {
        // Given
        val breed = "golden"
        val subBreed: String? = null

        // When
        val result = buildBreedKey(subBreed, breed)

        // Then
        assertEquals(breed, result)
    }

    @Test
    fun `when subBreed is not null, breed and subBreed should be concatenated with a slash`() {
        // Given
        val breed = "golden"
        val subBreed = "retriever"

        // When
        val result = buildBreedKey(subBreed, breed)

        // Then
        assertEquals("$breed/$subBreed", result)
    }

    @Test
    fun `when breed is empty and subBreed is not null, only slash and subBreed should be returned`() {
        // Given
        val breed = ""
        val subBreed = "retriever"

        // When
        val result = buildBreedKey(subBreed, breed)

        // Then
        assertEquals("/$subBreed", result)
    }

    @Test
    fun `when both breed and subBreed are empty, only a slash should be returned`() {
        // Given
        val breed = ""
        val subBreed = ""

        // When
        val result = buildBreedKey(subBreed, breed)

        // Then
        assertEquals("/", result)
    }
}