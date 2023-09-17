package dk.shape.dogbreeds.api

import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import javax.inject.Inject

interface BaseResponse<T> {
    val status: String
    val message: T
}

@Serializable
data class BreedResponse(
    override val status: String,
    override val message: Map<String, List<String>>
) : BaseResponse<Map<String, List<String>>>

@Serializable
data class BreedImagesResponse(
    override val status: String,
    override val message: List<String>
) : BaseResponse<List<String>>

private const val BASE_URL = "https://dog.ceo/api/"

class BreedsApi @Inject constructor(private val client: MyHttpClient) {

    suspend fun getAllBreeds(): ApiResponse<Map<String, List<String>>> {
        return client.safeApiCall<BreedResponse, Map<String, List<String>>> {
            get("${BASE_URL}breeds/list/all")
        }
    }

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
    suspend fun getBreedImages(breed: String): ApiResponse<List<String>> {
        return client.safeApiCall<BreedImagesResponse, List<String>> {
            get("${BASE_URL}breed/$breed/images")
        }
    }
}