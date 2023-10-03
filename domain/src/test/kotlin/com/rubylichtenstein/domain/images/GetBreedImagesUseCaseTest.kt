package com.rubylichtenstein.domain.images

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetBreedImagesUseCaseTest {
    private lateinit var useCase: GetBreedImagesUseCase
    private lateinit var fakeImagesRepository: FakeImagesRepository

    @BeforeEach
    fun setUp() {
        val element1 = DogImage(
            url = "sampleUrl1.com",
            isFavorite = false,
            breedName = "Dog",
            breedKey = "Dog"
        )

        val element2 = DogImage(
            url = "sampleUrl2.com",
            isFavorite = true,
            breedName = "Dog",
            breedKey = "Dog"
        )

        val images = listOf(
            element1,
            element2
        )

        fakeImagesRepository = FakeImagesRepository(images)
        useCase = GetBreedImagesUseCase(fakeImagesRepository)
    }

    @Test
    operator fun invoke() = runTest {
        useCase.invoke("Dog").test {
            val initialState = awaitItem()
            Assertions.assertEquals(2, initialState.size)
        }
    }
}