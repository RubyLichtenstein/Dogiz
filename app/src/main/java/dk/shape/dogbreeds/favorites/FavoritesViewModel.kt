package dk.shape.dogbreeds.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.shape.dogbreeds.common.AsyncState
import dk.shape.dogbreeds.model.BreedImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favoriteImagesState =
        MutableStateFlow<AsyncState<List<BreedImage>>>(AsyncState.Loading)
    val favoriteImagesState: StateFlow<AsyncState<List<BreedImage>>> get() = _favoriteImagesState

    init {
        viewModelScope.launch {
            favoritesRepository.favoriteImagesFlow.collect { asyncState ->
                _favoriteImagesState.value = asyncState
            }
        }
    }

    val favoriteCount: StateFlow<Int> = favoriteImagesState.map {
        when (it) {
            is AsyncState.Success -> it.data.size
            else -> 0
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    val favoriteImages: StateFlow<List<BreedImage>> = favoriteImagesState.map {
        when (it) {
            is AsyncState.Success -> it.data
            else -> emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun toggleFavorite(breedImage: BreedImage) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(breedImage)
        }
    }
}