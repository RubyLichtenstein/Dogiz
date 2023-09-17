package dk.shape.dogbreeds.breeds

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dk.shape.dogbreeds.api.ApiResponse
import dk.shape.dogbreeds.api.BreedsApi
import dk.shape.dogbreeds.common.AsyncState
import dk.shape.dogbreeds.di.AppModule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@UninstallModules(AppModule::class) // Assuming BreedsModule is where BreedsApi and BreedsDataStore are provided
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BreedsRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var breedsRepository: BreedsRepository

    @Inject
    lateinit var breedsApi: BreedsApi

    @Inject
    lateinit var breedsDataStore: BreedsDataStore

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(
            this,
            relaxUnitFun = true
        ) // This will initialize any @MockK annotated properties
    }

    @Test
    fun testGetBreeds_whenApiCallSuccessful() = runBlocking {
        // Mock API response
        coEvery { breedsApi.getAllBreeds() } returns ApiResponse.Success(mapOf())

        // Mock DataStore response
        coEvery { breedsDataStore.get } returns flowOf(emptyList())

        val testScope = CoroutineScope(Job() + Dispatchers.IO)
        breedsRepository.getBreeds(testScope)

        // Allow the method to execute
        delay(1000)  // You may need to adjust the delay based on the actual execution time

        val state = breedsRepository.breedsState.first()
        assertTrue(state is AsyncState.Success && state.data.isEmpty())
    }

    @After
    fun cleanup() {
        // Clear all mocks after test
        clearAllMocks()
    }
}
