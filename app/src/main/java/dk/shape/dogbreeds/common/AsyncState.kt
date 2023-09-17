package dk.shape.dogbreeds.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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

@Composable
fun <T> AsyncStateHandler(
    asyncState: AsyncState<T>,
    onError: @Composable (String) -> Unit = { message ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = message,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    },
    onLoading: @Composable () -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    },
    onSuccess: @Composable (T) -> Unit
) {
    when (asyncState) {
        is AsyncState.Loading -> onLoading()
        is AsyncState.Success -> onSuccess(asyncState.data)
        is AsyncState.Error -> onError(asyncState.message)
    }
}