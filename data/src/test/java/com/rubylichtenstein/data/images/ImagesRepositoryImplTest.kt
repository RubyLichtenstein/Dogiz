package com.rubylichtenstein.data.images

import app.cash.turbine.test
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ImagesRepositoryImplTest {

    // 1. Create Fake/Stubs
    class FakeDogBreedApi : BreedImagesApi {

        // Default behavior is to return a successful empty list.
        var getBreedImagesMock: suspend (String) -> Result<List<String>> =
            { breed -> Result.success(emptyList()) }

        override suspend fun getBreedImages(breed: String): Result<List<String>> {
            return getBreedImagesMock(breed)
        }
    }

    class FakeDogImageDao : DogImageDao {
        private val _storedImages = MutableStateFlow<List<DogImageDataEntity>>(emptyList())

        override fun getDogImagesByBreedKey(breedKey: String): Flow<List<DogImageDataEntity>> {
            return _storedImages.map { it.filter { it.breedKey == breedKey } }
        }

        override fun getFavoriteDogImages(): Flow<List<DogImageDataEntity>> {
            return _storedImages.map { it.filter { it.isFavorite } }
        }

        override fun updateFavoriteStatus(url: String, isFavorite: Boolean): Int {
            val currentImages = _storedImages.value
            val updatedImages = currentImages.map {
                if (it.url == url) it.copy(isFavorite = isFavorite) else it
            }
            _storedImages.value = updatedImages
            return 1
        }

        override fun insertAll(dogImages: List<DogImageDataEntity>) {
            _storedImages.value = dogImages
        }
    }

    // 2. Instantiate the Fakes
    private val fakeApi = FakeDogBreedApi()
    private val fakeDao = FakeDogImageDao()
    private val repository = ImagesRepositoryImpl(fakeApi, fakeDao)

    @Test
    fun `getImagesByBreed fetches from remote when local is empty and stores locally`() =
        runTest {
            // Given
            fakeApi.getBreedImagesMock = { breed ->
                Result.success(listOf("url1", "url2"))
            }

            // Act & Assert
            repository.getImagesByBreed("breedKey").test {
                assertEquals(AsyncResult.Loading, awaitItem())
                val successData = awaitItem() as AsyncResult.Success<List<DogImageEntity>>
                assertEquals(2, successData.data.size)
                assertEquals("url1", successData.data[0].url)
                assertEquals("url2", successData.data[1].url)
            }
        }

    @Test
    fun `getImagesByBreed updates local data when remote data is fetched`() = runTest {
        // Given
        fakeApi.getBreedImagesMock = { breed ->
            Result.success(listOf("url3", "url4"))
        }

        val localData = DogImageDataEntity("breedName", "breedKey", false, "localUrl")
        fakeDao.insertAll(listOf(localData))

        // Act
        val flowData = repository.getImagesByBreed("breedKey").take(2).toList()

        // Assert
        assertTrue(flowData[0] is AsyncResult.Loading)
        val successData = flowData[1] as AsyncResult.Success<List<DogImageEntity>>
        assertEquals(2, successData.data.size)
        assertEquals("url3", successData.data[0].url)
        assertEquals("url4", successData.data[1].url)
    }

    @Test
    fun `getImagesByBreed fetches from remote when local is empty`() = runTest {
        // Given
        fakeApi.getBreedImagesMock = { breed ->
            Result.success(listOf("url5", "url6"))
        }

        // Act
        val flowData = repository.getImagesByBreed("breedKey")
            .take(2)
            .toList()

        // Assert
        assertTrue(flowData[0] is AsyncResult.Loading)
        println(flowData)
        val successData = flowData[1] as AsyncResult.Success<List<DogImageEntity>>
        assertEquals(2, successData.data.size)
        assertEquals("url5", successData.data[0].url)
        assertEquals("url6", successData.data[1].url)
    }

    @Test
    fun `getImagesByBreed saves remote data to local storage even when local data exists`() =
        runTest {
            // Given
            fakeApi.getBreedImagesMock = { breed ->
                Result.success(listOf("url7", "url8"))
            }

            val localData = DogImageDataEntity("breedName", "breedKey", false, "localUrl")
            fakeDao.insertAll(listOf(localData))

            // Act
            repository.getImagesByBreed("breedKey").take(2).toList()

            // Assert that the new remote data has replaced the old local data
            val savedLocalData = fakeDao.getDogImagesByBreedKey("breedKey").first()
            assertTrue(savedLocalData.any { it.url == "url7" })
            assertTrue(savedLocalData.any { it.url == "url8" })
        }

    @Test
    fun `getImagesByBreed returns error when remote fetch fails`() = runTest {
        // Given
        // Given
        fakeApi.getBreedImagesMock = { breed ->
            Result.success(emptyList())
        }

        fakeApi.getBreedImagesMock = { breed ->
            Result.failure(Exception("API failed"))
        }
        // Act
        val flowData = repository.getImagesByBreed("breedKey").toList()

        // Assert
        assertTrue(flowData[0] is AsyncResult.Loading)
        assertTrue(flowData[1] is AsyncResult.Error)
    }

}
