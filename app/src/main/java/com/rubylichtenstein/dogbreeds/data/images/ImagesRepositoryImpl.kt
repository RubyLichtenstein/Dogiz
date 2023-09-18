package com.rubylichtenstein.dogbreeds.data.images

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asResult
import com.rubylichtenstein.domain.images.DogImageData
import com.rubylichtenstein.domain.images.DogImageDataImpl
import com.rubylichtenstein.domain.images.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepositoryImpl @Inject constructor(
    private val dogBreedApiService: BreedImagesApi,
    private val imagesDataStore: ImagesDataStore
) : ImagesRepository {
    override fun getImagesByBreed(breed: String): Flow<AsyncResult<List<DogImageData>>> = flow {
        val localImages = getImagesByBreedFromLocal(breed)
        if (localImages != null) {
            emit(localImages)
        }

        try {
            val remoteImages = dogBreedApiService.getBreedImages(breed).getOrThrow()
            val images = remoteImages.map { imageUrl -> DogImageDataImpl(breed, imageUrl) }
            imagesDataStore.save(images, breed)
            emit(images)
        } catch (e: Exception) {
            val fallbackLocalImages = getImagesByBreedFromLocal(breed)
            if (fallbackLocalImages != null) {
                emit(fallbackLocalImages)
            } else {
                throw e
            }
        }
    }.asResult()

    private suspend fun getImagesByBreedFromLocal(breed: String): List<DogImageData>? {
        return imagesDataStore.getByBreed(breed).first()
    }
}