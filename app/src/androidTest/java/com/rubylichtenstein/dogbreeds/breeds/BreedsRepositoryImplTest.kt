package com.rubylichtenstein.dogbreeds.breeds

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import com.rubylichtenstein.dogbreeds.di.AppModule
import org.junit.runner.RunWith

@UninstallModules(AppModule::class) // Assuming BreedsModule is where BreedsApi and BreedsDataStore are provided
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BreedsRepositoryImplTest {

//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @Inject
//    lateinit var breedsRepository: BreedsRepository
//
//    @Inject
//    lateinit var breedsApi: BreedsApi
//
//    @Inject
//    lateinit var breedsDataStore: BreedsDataStore
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//        MockKAnnotations.init(
//            this,
//            relaxUnitFun = true
//        ) // This will initialize any @MockK annotated properties
//    }
//
//    @Test
//    fun testGetBreeds_whenApiCallSuccessful() = runBlocking {
//        // Mock API response
//        coEvery { breedsApi.getAllBreeds() } returns ApiResponse.Success(mapOf())
//
//        // Mock DataStore response
//        coEvery { breedsDataStore.get } returns flowOf(emptyList())
//
//        val testScope = CoroutineScope(Job() + Dispatchers.IO)
////        breedsRepository.getBreeds(testScope)
//
//        // Allow the method to execute
//        delay(1000)  // You may need to adjust the delay based on the actual execution time
//
//        val state = breedsRepository.breedsFlow.first()
//        assertTrue(state is AsyncState.Success && state.data.isEmpty())
//    }
//
//    @After
//    fun cleanup() {
//        // Clear all mocks after test
//        clearAllMocks()
//    }
}
