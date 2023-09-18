package com.rubylichtenstein.dogbreeds.data.breeds

import com.rubylichtenstein.dogbreeds.data.MyHttpClient
import com.rubylichtenstein.dogbreeds.data.images.BASE_URL
import com.rubylichtenstein.domain.breeds.data.BreedsApiSource
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