package dk.shape.dogbreeds.data.breeds

import dk.shape.domain.common.AsyncResult
import dk.shape.domain.breeds.BreedInfo
import dk.shape.domain.breeds.BreedsRepository
import dk.shape.domain.common.asResult
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

    override val breedsFlow: Flow<AsyncResult<List<BreedInfo>>> = flow {
        // Try fetching from local first
        val localBreeds = getBreedsFromLocal()
        if (localBreeds != null) {
            emit(localBreeds)
        }

        // Then always fetch from remote
        try {
            val remoteBreeds = breedsApi.getAllBreeds().getOrThrow()
            val breeds = BreedInfo.fromMap(remoteBreeds)

            if (breeds != localBreeds) {
                // Only save and emit if they are different
                breedsDataStore.save(breeds)
                emit(breeds)
            }
        } catch (exception: Exception) {
            if (localBreeds == null) {
                throw exception
            }
        }
    }.asResult()

    private suspend fun getBreedsFromLocal(): List<BreedInfo>? {
        return breedsDataStore.get.first()
    }
}


