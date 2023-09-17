package dk.shape.dogbreeds.data.breeds

import dk.shape.domain.common.AsyncState
import dk.shape.domain.breeds.BreedInfo
import dk.shape.domain.breeds.BreedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsRepositoryImpl @Inject constructor(
    private val breedsApi: BreedsRemoteApi,
    private val breedsDataStore: BreedsDataStore
) : BreedsRepository {

    override val breedsFlow: Flow<AsyncState<List<BreedInfo>>> = flow {
        // Emit loading state
        emit(AsyncState.Loading)

        // Try fetching from local first
        val localBreeds = getBreedsFromLocal()
        if (localBreeds != null) {
            emit(AsyncState.Success(localBreeds))
        } else {
            // If not available, fetch from remote
            breedsApi.getAllBreeds()
                .onSuccess {
                    val breeds = BreedInfo.fromMap(it)
                    breedsDataStore.save(breeds)
                    emit(AsyncState.Success(breeds))
                }
                .onFailure {
                    emit(AsyncState.Error(it.message ?: "Unknown error"))
                }
        }
    }

    private suspend fun getBreedsFromLocal(): List<BreedInfo>? {
        return breedsDataStore.get.first()
    }
}


