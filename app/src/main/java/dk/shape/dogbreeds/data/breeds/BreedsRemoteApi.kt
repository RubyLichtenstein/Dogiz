package dk.shape.dogbreeds.data.breeds

import dk.shape.dogbreeds.data.MyHttpClient
import dk.shape.dogbreeds.data.images.BASE_URL
import dk.shape.domain.breeds.BreedsApiSource
import io.ktor.client.request.get
import javax.inject.Inject

class BreedsRemoteApi @Inject constructor(private val client: MyHttpClient) :
    BreedsApiSource {

    override suspend fun getAllBreeds(): Result<Map<String, List<String>>> {
        return client.safeApiCall {
            get("${BASE_URL}breeds/list/all")
        }
    }
}