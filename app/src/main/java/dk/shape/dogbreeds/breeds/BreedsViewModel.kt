package dk.shape.dogbreeds.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.shape.dogbreeds.common.AsyncState
import dk.shape.dogbreeds.common.mapSuccess
import dk.shape.dogbreeds.model.BreedInfo
import dk.shape.dogbreeds.model.BreedItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository
) : ViewModel() {

    val breedsState: StateFlow<AsyncState<List<BreedItem>>> = breedsRepository.breedsState
        .map { it.mapSuccess { data -> fromBreedInfoListToBreedItemList(data) } }
        .stateIn(viewModelScope, SharingStarted.Lazily, AsyncState.Loading)

    init {
        fetchDogBreeds()
    }

    private fun fetchDogBreeds() {
        breedsRepository.getBreeds(viewModelScope)
    }

    private fun fromBreedInfoListToBreedItemList(
        breedInfoList: List<BreedInfo>
    ): List<BreedItem> {
        return breedInfoList.flatMap { breedInfo ->
            val subBreedItems = breedInfo.subBreeds.map { BreedItem.SubBreed(it, breedInfo.breed) }
            listOf(BreedItem.Breed(breedInfo.breed, subBreedItems)) + subBreedItems
        }
    }
}
