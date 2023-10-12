package com.rubylichtenstein.data.images

import app.cash.turbine.test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class ImagesRepositoryImplTest {

    // 1. Create Fake/Stubs
    class FakeDogBreedApi : BreedImagesApi {

        // Default behavior is to return a successful empty list.
        var getBreedImagesMock: suspend (String) -> List<String> =
            { breed -> emptyList() }

        override suspend fun getBreedImages(breed: String): List<String> {
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
    fun `getImagesByBreed fetches from remote when local is empty and stores locally`() = runTest {
        // Given
        fakeApi.getBreedImagesMock = { breed ->
            listOf("url1", "url2")
        }

        // Act & Assert
        repository.getImagesByBreed("breedKey").test {
            val successData = awaitItem()
            successData.size shouldBe 2
            successData[0].url shouldBe "url1"
            successData[1].url shouldBe "url2"

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getImagesByBreed updates local data when remote data is fetched`() = runTest {
        // Given
        fakeApi.getBreedImagesMock = { breed ->
            listOf("url3", "url4")
        }

        val localData = DogImageDataEntity("breedName", "breedKey", false, "localUrl")
        fakeDao.insertAll(listOf(localData))

        // Act & Assert
        repository.getImagesByBreed("breedKey").test {
            val successData = awaitItem()
            successData.size shouldBe 2
            successData[0].url shouldBe "url3"
            successData[1].url shouldBe "url4"

            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `getImagesByBreed fetches from remote when local is empty`() = runTest {
        // Given
        fakeApi.getBreedImagesMock = { breed ->
            listOf("url5", "url6")
        }

        // Act & Assert
        repository.getImagesByBreed("breedKey").test {
            val successData = awaitItem()
            successData.map { it.url } shouldContainExactlyInAnyOrder listOf("url5", "url6")

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getImagesByBreed saves remote data to local storage even when local data exists`() =
        runTest {
            // Given
            fakeApi.getBreedImagesMock = { breed ->
                listOf("url7", "url8")
            }

            val localData = DogImageDataEntity("breedName", "breedKey", false, "localUrl")
            fakeDao.insertAll(listOf(localData))

            // Act & Assert
            repository.getImagesByBreed("breedKey").test {
                awaitItem()

                val savedLocalData = fakeDao.getDogImagesByBreedKey("breedKey").first()
                savedLocalData.any { it.url == "url7" }.shouldBeTrue()
                savedLocalData.any { it.url == "url8" }.shouldBeTrue()

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `getImagesByBreed returns error when remote fetch fails`() = runTest {
        // Given
        fakeApi.getBreedImagesMock = { breed ->
            error("API failed")
        }

        // Act & Assert
        repository.getImagesByBreed("breedKey").test {
            val errorData = awaitError()
            errorData.message shouldBe "API failed"
            cancelAndConsumeRemainingEvents()
        }
    }
}
