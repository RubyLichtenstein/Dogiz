package com.rubylichtenstein.domain.images

data class DogImage(
    val breedName: String,
    val breedKey: String,
    val isFavorite: Boolean,
    val url: String
)

fun buildBreedKey(subBreed: String?, breed: String) =
    if (subBreed == null) breed else "$breed/$subBreed"