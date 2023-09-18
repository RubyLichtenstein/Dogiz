package dk.shape.domain.breeds

import dk.shape.domain.common.AsyncResult
import dk.shape.domain.common.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(
    private val breedsRepository: BreedsRepository
) {
    operator fun invoke(): Flow<AsyncResult<List<BreedItem>>> {
        return breedsRepository.breedsFlow.map {
            it.mapSuccess { data -> mapInfoToItems(data) }
        }
    }

    private fun mapInfoToItems(
        breedInfoList: List<BreedInfo>
    ): List<BreedItem> {
        return breedInfoList.flatMap { breedInfo ->
            val subBreedItems =
                breedInfo.subBreeds.map { BreedItem.SubBreed(it, breedInfo.breed) }
            listOf(BreedItem.Breed(breedInfo.breed, subBreedItems)) + subBreedItems
        }
    }
}
