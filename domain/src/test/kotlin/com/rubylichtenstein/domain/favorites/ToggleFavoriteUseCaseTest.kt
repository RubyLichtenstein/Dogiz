package com.rubylichtenstein.domain.favorites

import app.cash.turbine.test
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ToggleFavoriteUseCaseTest {

    @Test
    fun `invoke updates favorite status correctly`() = runTest {
        // Given
        val element1 = DogImageEntity(
            url = "sampleUrl1.com",
            isFavorite = false,
            breedName = "Dog",
            breedKey = "dog"
        )

        val element2 = DogImageEntity(
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
            assertEquals(AsyncResult.Loading, awaitItem())

            val initialState = awaitItem() as AsyncResult.Success
            assertEquals(1, initialState.data.size)
            assertEquals("sampleUrl2.com", initialState.data[0].url)

            toggleFavoriteUseCase(element1)
            // After toggling, both images should be favorite
            val afterToggle1 = awaitItem() as AsyncResult.Success
            assertEquals(2, afterToggle1.data.size)
            assertEquals("sampleUrl1.com", afterToggle1.data[0].url)
            assertEquals("sampleUrl2.com", afterToggle1.data[1].url)

            toggleFavoriteUseCase(element2)
            // After toggling again, only the first image should remain as favorite
            val afterToggle2 = awaitItem() as AsyncResult.Success
            assertEquals(1, afterToggle2.data.size)
            assertEquals("sampleUrl1.com", afterToggle2.data[0].url)

            cancelAndConsumeRemainingEvents() // End the test
        }
    }
}
