package com.rubylichtenstein.data.breeds

import com.rubylichtenstein.data.KtorHttpClient
import com.rubylichtenstein.data.images.BASE_URL
import io.ktor.client.request.get
import javax.inject.Inject

class BreedsRemoteApi @Inject constructor(private val client: KtorHttpClient) {

    suspend fun getAllBreeds(): Map<String, List<String>> {
        return client.safeApiCall {
            get("${BASE_URL}breeds/list/all")
        }
    }
}