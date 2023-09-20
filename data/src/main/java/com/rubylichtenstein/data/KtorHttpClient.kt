package com.rubylichtenstein.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ApiResponse<T>(
    val status: String,
    val message: T
)

class KtorHttpClient {
    val client: HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    suspend inline fun <reified T : ApiResponse<U>, U> safeApiCall(
        apiCall: HttpClient.() -> HttpResponse
    ): Result<U> = runCatching {
        val response = apiCall.invoke(client)
        when (response.status) {
            HttpStatusCode.OK -> {
                val obj = response.body<T>()
                if (obj.status != "success") {
                    throw Error("Server responded with status: ${obj.status}")
                } else {
                    obj.message
                }
            }

            else -> throw Error("An unknown or network error occurred")
        }
    }
}