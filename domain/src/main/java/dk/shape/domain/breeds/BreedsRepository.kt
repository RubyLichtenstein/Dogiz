package dk.shape.domain.breeds

import dk.shape.domain.common.AsyncState
import kotlinx.coroutines.flow.Flow

interface BreedsRepository {
    val breedsFlow: Flow<AsyncState<List<BreedInfo>>>
}