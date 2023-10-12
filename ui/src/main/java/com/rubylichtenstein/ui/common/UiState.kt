package com.rubylichtenstein.ui.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T, R> UiState<T>.mapSuccess(transform: (T) -> R): UiState<R> {
    return when (this) {
        is UiState.Loading -> UiState.Loading
        is UiState.Success -> UiState.Success(transform(data))
        is UiState.Error -> UiState.Error(message)
        is UiState.Empty -> UiState.Empty(message)
    }
}

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String?) : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Empty(val message: String?) : UiState<Nothing>
}

fun <T> Flow<T>.asUiState(
    errorMessage: String? = null
): Flow<UiState<T>> =
    map<T, UiState<T>> {
        UiState.Success(it)
    }.catch {
        emit(UiState.Error(errorMessage ?: it.message))
    }

fun <T> Flow<List<T>>.asUiState(
    emptyMessage: String? = null,
    errorMessage: String? = null
): Flow<UiState<List<T>>> =
    map {
        if (it.isEmpty()) {
            UiState.Empty(emptyMessage)
        } else {
            UiState.Success(it)
        }
    }.catch {
        emit(UiState.Error(errorMessage ?: it.message))
    }
