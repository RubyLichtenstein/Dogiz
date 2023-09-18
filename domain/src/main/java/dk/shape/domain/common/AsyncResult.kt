package dk.shape.domain.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T, R> AsyncResult<T>.mapSuccess(transform: (T) -> R): AsyncResult<R> {
    return when (this) {
        is AsyncResult.Loading -> AsyncResult.Loading
        is AsyncResult.Success -> AsyncResult.Success(transform(data))
        is AsyncResult.Error -> AsyncResult.Error(exception)
    }
}

sealed interface AsyncResult<out T> {
    data class Success<T>(val data: T) : AsyncResult<T>
    data class Error(val exception: Throwable? = null) : AsyncResult<Nothing>
    data object Loading : AsyncResult<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<AsyncResult<T>> {
    return this
        .map<T, AsyncResult<T>> {
            AsyncResult.Success(it)
        }
        .onStart { emit(AsyncResult.Loading) }
        .catch { emit(AsyncResult.Error(it)) }
}