package dk.shape.domain.common

sealed class AsyncState<out T> {
    data object Loading : AsyncState<Nothing>()
    data class Success<T>(val data: T) : AsyncState<T>()
    data class Error(val message: String) : AsyncState<Nothing>()
}

fun <T, R> AsyncState<T>.mapSuccess(transform: (T) -> R): AsyncState<R> {
    return when (this) {
        is AsyncState.Loading -> AsyncState.Loading
        is AsyncState.Success -> AsyncState.Success(transform(data))
        is AsyncState.Error -> AsyncState.Error(message)
    }
}
