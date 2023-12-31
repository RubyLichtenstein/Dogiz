package com.rubylichtenstein.domain.images

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeImagesRepository(
    initialDogImages: List<DogImage>
) : ImagesRepository {

    private val _images = MutableStateFlow(initialDogImages)

    override fun getImagesByBreed(breedKey: String): Flow<List<DogImage>> {
        return _images.map { images ->
            images.filter { it.breedKey == breedKey }
        }
    }
}
