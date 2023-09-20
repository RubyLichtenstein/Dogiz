package com.rubylichtenstein.data.images

import com.rubylichtenstein.data.images.DogImageDataEntity.Companion.fromDogImageEntity
import com.rubylichtenstein.data.images.DogImageDataEntity.Companion.toDogImageEntity
import com.rubylichtenstein.domain.breeds.buildDisplayNameFromKey
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asAsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import com.rubylichtenstein.domain.images.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepositoryImpl @Inject constructor(
    private val dogBreedApiService: BreedImagesApi,
    private val imagesDataStore: DogImageDao
) : ImagesRepository {
    override fun getImagesByBreed(breedKey: String): Flow<AsyncResult<List<DogImageEntity>>> =
        getImagesByBreedFromLocal(breedKey)
            .onStart { fetchAndSave(breedKey) }
            .distinctUntilChanged()
            .asAsyncResult()

    private suspend fun fetchAndSave(breedKey: String) {
        val remoteData = getRemoteBreedImages(breedKey).getOrThrow()
        imagesDataStore.insertAll(remoteData.map { it.fromDogImageEntity() })
    }

    private suspend fun getRemoteBreedImages(breedKey: String): Result<List<DogImageEntity>> {
        return dogBreedApiService.getBreedImages(breedKey).map {
            it.map { url ->
                DogImageEntity(
                    breedName = buildDisplayNameFromKey(breedKey),
                    isFavorite = false,
                    url = url,
                    breedKey = breedKey
                )
            }
        }
    }

    private fun getImagesByBreedFromLocal(breedKey: String): Flow<List<DogImageEntity>> {
        return imagesDataStore.getDogImagesByBreedKey(breedKey).map {
            it.map { entity ->
                entity.toDogImageEntity()
            }
        }
    }
}



