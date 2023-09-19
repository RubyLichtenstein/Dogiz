package com.rubylichtenstein.data.images

import com.rubylichtenstein.data.MyHttpClient
import io.ktor.client.request.get
import javax.inject.Inject

const val BASE_URL = "https://dog.ceo/api/"

class BreedImagesApi @Inject constructor(private val client: MyHttpClient) {

    /**
     * Fetches a list of image URLs for a given dog breed or sub-breed.
     *
     * @param breed The name of the dog breed or sub-breed in the format "breed/subBreed".
     *              For example, for a sub-breed like "Australian Shepherd", you would use "shepherd/australian".
     *              For a main breed like "bulldog", just use "bulldog".
     *
     * @return ApiResponse<List<String>> A response object containing either a list of image URLs or an error.
     *
     * Usage:
     * 1. To get images for a main breed:
     *      getBreedImages("bulldog")
     * 2. To get images for a sub-breed:
     *      getBreedImages("shepherd/australian")
     */
    suspend fun getBreedImages(breed: String): Result<List<String>> {
        return client.safeApiCall {
            get("${com.rubylichtenstein.data.images.BASE_URL}breed/$breed/images")
        }
    }
}

