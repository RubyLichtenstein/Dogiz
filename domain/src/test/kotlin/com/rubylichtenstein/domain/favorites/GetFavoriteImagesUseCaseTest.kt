package com.rubylichtenstein.domain.favorites

import app.cash.turbine.test
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetFavoriteImagesUseCaseTest {

    private lateinit var useCase: GetFavoriteImagesUseCase
    private lateinit var fakeFavoritesRepository: FakeFavoritesRepository

    @BeforeEach
    fun setUp() {
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

        fakeFavoritesRepository = FakeFavoritesRepository(images)
        useCase = GetFavoriteImagesUseCase(fakeFavoritesRepository)
    }

    @Test
    fun `when invoke is called, it should return favorite images from the repository`() = runTest {
        useCase.invoke().test {
            val initialState = awaitItem()
            Assertions.assertEquals(1, initialState.size)
            Assertions.assertEquals("sampleUrl2.com", initialState[0].url)
        }
    }
}