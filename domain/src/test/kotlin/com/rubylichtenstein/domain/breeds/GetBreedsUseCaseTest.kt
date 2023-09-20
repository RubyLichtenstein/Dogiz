package com.rubylichtenstein.domain.breeds

import com.rubylichtenstein.domain.breeds.BreedEntity
import com.rubylichtenstein.domain.breeds.GetBreedsUseCase
import com.rubylichtenstein.domain.breeds.data.BreedInfo
import com.rubylichtenstein.domain.breeds.data.BreedsRepository
import com.rubylichtenstein.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FakeBreedsRepository : BreedsRepository {
    var breedsData: AsyncResult<List<BreedInfo>> = AsyncResult.Success(emptyList())

    override val breedsFlow: Flow<AsyncResult<List<BreedInfo>>> = flow {
        emit(breedsData)
    }
}

class GetBreedsUseCaseTest {

    private val fakeBreedsRepository = FakeBreedsRepository()
    private val getBreedsUseCase = GetBreedsUseCase(fakeBreedsRepository)

    @Test
    fun `invoke returns mapped breed entities`() = runTest {
        // Given
        fakeBreedsRepository.breedsData = AsyncResult.Success(
            listOf(
                object : BreedInfo {
                    override val name: String = "Dog"
                    override val subBreedsNames: List<String> = listOf("Lab", "Husky")
                },
            )
        )

        // When
        val result = getBreedsUseCase.invoke().toList().first()

        // Then
        val expected = AsyncResult.Success(
            listOf(
                BreedEntity("Dog", "Dog"),
                BreedEntity("Dog (Lab)", "Dog_Lab"),
                BreedEntity("Dog (Husky)", "Dog_Husky")
            )
        )
        assertEquals(expected, result)
    }
}
