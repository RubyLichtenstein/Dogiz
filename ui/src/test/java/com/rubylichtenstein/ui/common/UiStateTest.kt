package com.rubylichtenstein.ui.common

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Locale

class UiStateTest {

    @Test
    fun `mapSuccess returns Loading when source is Loading`() = runTest {
        val source: UiState<String> = UiState.Loading
        val result = source.mapSuccess { it.uppercase(Locale.getDefault()) }
        assertTrue(result is UiState.Loading)
    }

    @Test
    fun `mapSuccess transforms Success data`() = runTest {
        val source: UiState<String> = UiState.Success("test")
        val result = source.mapSuccess { it.uppercase(Locale.getDefault()) }
        assertTrue(result is UiState.Success && result.data == "TEST")
    }

    @Test
    fun `mapSuccess returns Error when source is Error`() = runTest {
        val error = "Test Exception"
        val source: UiState<String> = UiState.Error(message = error)
        val result = source.mapSuccess { it.uppercase(Locale.getDefault()) }
        assertTrue(result is UiState.Error && result.message == error)
    }

    @Test
    fun `asAsyncResult emits Loading at start`() = runTest {
        val flow = flowOf("data").asUiState().toList()
        assertTrue(flow.first() is UiState.Loading)
    }

    @Test
    fun `asAsyncResult emits Success with data`() = runTest {
        val flow = flowOf("data").asUiState().toList()
        val result = flow.drop(1).first()
        assertTrue(result is UiState.Success && result.data == "data")
    }

    @Test
    fun `asAsyncResult emits Error on exception`() = runTest {
        val flow = flow<String> { throw Exception("Error") }.asUiState().toList()
        val result = flow.drop(1).first()
        assertTrue(result is UiState.Error && result?.message == "Error")
    }

    @Test
    fun `asAsyncResult emits Error`() = runTest {
        val flow = flow { emit(UiState.Error("Error")) }.toList()
        val result = flow.first()
        assertTrue(result?.message == "Error")
    }
}
