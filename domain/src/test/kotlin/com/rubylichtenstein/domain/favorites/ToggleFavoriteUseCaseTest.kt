package com.rubylichtenstein.domain.favorites

import app.cash.turbine.test
import com.rubylichtenstein.domain.images.DogImage
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ToggleFavoriteUseCaseTest {

    @Test
    fun `invoke updates favorite status correctly`() = runTest {
        // Given
        val element1 = DogImage(
            url = "sampleUrl1.com",
            isFavorite = false,
            breedName = "Dog",
            breedKey = "dog"
        )

        val element2 = DogImage(
            url = "sampleUrl2.com",
            isFavorite = true,
            breedName = "Dog",
            breedKey = "dog"
        )

        val images = listOf(
            element1,
            element2
        )

        val fakeFavoritesRepository = FakeFavoritesRepository(images)
        val toggleFavoriteUseCase = ToggleFavoriteUseCase(fakeFavoritesRepository)

        // Act
        fakeFavoritesRepository.favoriteImagesFlow.test {

            val initialState = awaitItem()
            assertEquals(1, initialState.size)
            assertEquals("sampleUrl2.com", initialState[0].url)

            toggleFavoriteUseCase(element1)
            // After toggling, both images should be favorite
            val afterToggle1 = awaitItem()
            assertEquals(2, afterToggle1.size)
            assertEquals("sampleUrl1.com", afterToggle1[0].url)
            assertEquals("sampleUrl2.com", afterToggle1[1].url)

            toggleFavoriteUseCase(element2)
            // After toggling again, only the first image should remain as favorite
            val afterToggle2 = awaitItem()
            assertEquals(1, afterToggle2.size)
            assertEquals("sampleUrl1.com", afterToggle2[0].url)

            cancelAndConsumeRemainingEvents() // End the test
        }
    }
}
