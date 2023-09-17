package dk.shape.dogbreeds.breeds

import dk.shape.dogbreeds.api.ApiResponse
import dk.shape.dogbreeds.api.BreedsApi
import dk.shape.dogbreeds.common.AsyncState
import dk.shape.dogbreeds.model.BreedInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsRepository @Inject constructor(
    private val breedsApi: BreedsApi,
    private val breedsDataStore: BreedsDataStore
) {
    private val _breedsState = MutableStateFlow<AsyncState<List<BreedInfo>>>(AsyncState.Loading)
    val breedsState: StateFlow<AsyncState<List<BreedInfo>>> get() = _breedsState

    private suspend fun fetchRemoteAndSaveToLocal() {
        when (val breedResponse = breedsApi.getAllBreeds()) {
            is ApiResponse.Success -> {
                val breeds = BreedInfo.fromMap(breedResponse.data)
                breedsDataStore.save(breeds)
                _breedsState.value = AsyncState.Success(breeds)
            }

            is ApiResponse.Error -> {
                val localBreeds = getBreedsFromLocal()
                if (localBreeds != null) {
                    _breedsState.value = AsyncState.Success(localBreeds)
                } else {
                    _breedsState.value = AsyncState.Error(breedResponse.message)
                }
            }
        }
    }

    private suspend fun getBreedsFromLocal(): List<BreedInfo>? {
        return breedsDataStore.get.first()
    }

    fun getBreeds(scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            val localBreeds = getBreedsFromLocal()

            if (localBreeds != null) {
                _breedsState.value = AsyncState.Success(localBreeds)
            }

            fetchRemoteAndSaveToLocal()
        }
    }
}
