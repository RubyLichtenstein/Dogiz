package dk.shape.domain.breeds

import dk.shape.domain.common.AsyncResult
import kotlinx.coroutines.flow.Flow

interface BreedsRepository {
    val breedsFlow: Flow<AsyncResult<List<BreedInfo>>>
}