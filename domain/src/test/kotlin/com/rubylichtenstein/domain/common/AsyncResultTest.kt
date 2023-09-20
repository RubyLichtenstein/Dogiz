package com.rubylichtenstein.domain.common

import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asAsyncResult
import com.rubylichtenstein.domain.common.mapSuccess
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Locale

class AsyncResultTest {

    @Test
    fun `mapSuccess returns Loading when source is Loading`() = runTest {
        val source: AsyncResult<String> = AsyncResult.Loading
        val result = source.mapSuccess { it.uppercase(Locale.getDefault()) }
        assertTrue(result is AsyncResult.Loading)
    }

    @Test
    fun `mapSuccess transforms Success data`() = runTest {
        val source: AsyncResult<String> = AsyncResult.Success("test")
        val result = source.mapSuccess { it.uppercase(Locale.getDefault()) }
        assertTrue(result is AsyncResult.Success && result.data == "TEST")
    }

    @Test
    fun `mapSuccess returns Error when source is Error`() = runTest {
        val exception = Exception("Test Exception")
        val source: AsyncResult<String> = AsyncResult.Error(exception)
        val result = source.mapSuccess { it.uppercase(Locale.getDefault()) }
        assertTrue(result is AsyncResult.Error && result.exception == exception)
    }

    @Test
    fun `asAsyncResult emits Loading at start`() = runTest {
        val flow = flowOf("data").asAsyncResult().toList()
        assertTrue(flow.first() is AsyncResult.Loading)
    }

    @Test
    fun `asAsyncResult emits Success with data`() = runTest {
        val flow = flowOf("data").asAsyncResult().toList()
        val result = flow.drop(1).first()
        assertTrue(result is AsyncResult.Success && result.data == "data")
    }

    @Test
    fun `asAsyncResult emits Error on exception`() = runTest {
        val flow = flow<String> { throw Exception("Error") }.asAsyncResult().toList()
        val result = flow.drop(1).first()
        assertTrue(result is AsyncResult.Error && result.exception?.message == "Error")
    }
}
