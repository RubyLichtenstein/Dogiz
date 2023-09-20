package com.rubylichtenstein.domain.favorites

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class FakeFavoritesRepository : FavoritesRepository {
    var favoriteStatusUpdated = false
    var updatedUrl: String? = null
    var updatedStatus: Boolean? = null

    override val favoriteImagesFlow: Flow<AsyncResult<List<DogImageEntity>>> =
        flowOf() // Empty flow for simplicity.

    override suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        favoriteStatusUpdated = true
        updatedUrl = url
        updatedStatus = isFavorite
    }
}

class ToggleFavoriteUseCaseTest {

    private val fakeFavoritesRepository = FakeFavoritesRepository()
    private val toggleFavoriteUseCase = ToggleFavoriteUseCase(fakeFavoritesRepository)

    @Test
    fun `invoke updates favorite status correctly`() = runTest {
        // Given
        val dogImageEntity = DogImageEntity(
            url = "sampleUrl.com",
            isFavorite = false,
            breedName = "Dog",
        )

        // Act
        toggleFavoriteUseCase(dogImageEntity)

        // Assert
        assertTrue(fakeFavoritesRepository.favoriteStatusUpdated)
        assertEquals(dogImageEntity.url, fakeFavoritesRepository.updatedUrl)
        assertEquals(true, fakeFavoritesRepository.updatedStatus)
    }
}
