package dk.shape.dogbreeds.api

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
import kotlinx.serialization.json.Json

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
}

class MyHttpClient {
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

    suspend inline fun <reified T : BaseResponse<U>, U> safeApiCall(
        apiCall: HttpClient.() -> HttpResponse
    ): ApiResponse<U> {
        return try {
            val response = apiCall.invoke(client)

            when (response.status) {
                HttpStatusCode.OK -> {
                    val obj =
                        response.body<T>()
                    if (obj.status != "success") {
                        ApiResponse.Error("Server responded with status: ${obj.status}")
                    } else {
                        ApiResponse.Success(obj.message)
                    }
                }

                HttpStatusCode.Unauthorized -> ApiResponse.Error("Unauthorized")
                HttpStatusCode.Forbidden -> ApiResponse.Error("Forbidden")
                HttpStatusCode.NotFound -> ApiResponse.Error("Not Found")
                else -> ApiResponse.Error("An unknown or network error occurred")
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }
}