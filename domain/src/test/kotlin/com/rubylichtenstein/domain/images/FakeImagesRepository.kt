package com.rubylichtenstein.domain.images

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asAsyncResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeImagesRepository(
    initialDogImages: List<DogImageEntity>
) : ImagesRepository {

    private val _images = MutableStateFlow(initialDogImages)

    override fun getImagesByBreed(breedKey: String): Flow<AsyncResult<List<DogImageEntity>>> {
        return _images.map { images ->
            images.filter { it.breedKey == breedKey }
        }.asAsyncResult()
    }
}
