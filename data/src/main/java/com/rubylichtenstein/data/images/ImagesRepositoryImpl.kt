package com.rubylichtenstein.data.images

import com.rubylichtenstein.data.images.DogImageDataEntity.Companion
import com.rubylichtenstein.data.images.DogImageDataEntity.Companion.toDogImageEntity
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asAsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import com.rubylichtenstein.domain.images.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepositoryImpl @Inject constructor(
    private val dogBreedApiService: BreedImagesApi,
    private val imagesDataStore: DogImageDao
) : ImagesRepository {
    override fun getImagesByBreed(breed: String): Flow<AsyncResult<List<DogImageEntity>>> =
        getImagesByBreedFromLocal(breed)
            .onStart {
                // If local data is empty, fetch from remote and save to local
                val localData = imagesDataStore.getDogImagesByBreed(breed).first()
                if (localData.isEmpty()) {
                    val remoteData = getImagesByBreedFromRemote(breed).getOrThrow()
                    remoteData.let {
                        imagesDataStore.insertAll(it.map(Companion::fromDogImageEntity))
                    }
                }
            }
            .distinctUntilChanged()
            .asAsyncResult()

    private suspend fun getImagesByBreedFromRemote(breed: String): Result<List<DogImageEntity>> {
        return dogBreedApiService.getBreedImages(breed).map {
            it.map {
                DogImageEntity(breed, false, it)
            }
        }
    }

    private fun getImagesByBreedFromLocal(breed: String): Flow<List<DogImageEntity>> {
        return imagesDataStore.getDogImagesByBreed(breed).map {
            it.map { entity ->
                entity.toDogImageEntity()
            }
        }
    }
}



