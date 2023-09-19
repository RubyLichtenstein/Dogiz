package com.rubylichtenstein.data.breeds

import com.rubylichtenstein.data.MyHttpClient
import com.rubylichtenstein.data.images.BASE_URL
import io.ktor.client.request.get
import javax.inject.Inject

class BreedsRemoteApi @Inject constructor(private val client: MyHttpClient) {

    suspend fun getAllBreeds(): Result<Map<String, List<String>>> {
        return client.safeApiCall {
            get("${com.rubylichtenstein.data.images.BASE_URL}breeds/list/all")
        }
    }
}