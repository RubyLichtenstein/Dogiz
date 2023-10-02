package com.rubylichtenstein.ui.favorites

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.rubylichtenstein.domain.favorites.GetFavoriteImagesUseCase
import com.rubylichtenstein.domain.favorites.ToggleFavoriteUseCase
import com.rubylichtenstein.domain.images.DogImageEntity
import com.rubylichtenstein.ui.common.AsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : MoleculeViewModel<Event, AsyncResult<FavoritesModel>>() {

    @Composable
    override fun models(events: Flow<Event>): AsyncResult<FavoritesModel> {
        return FavoritesPresenter(
            events = events,
            favoriteImagesFlow = getFavoriteImagesUseCase()
        )
    }

    fun toggleBreedFilter(breed: ChipInfo) {
        take(Event.ToggleSelectedBreed(breed))
    }

    val favoriteCount: StateFlow<Int> = getFavoriteImagesUseCase().map {
        it.size
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    fun toggleFavorite(breedImage: DogImageEntity) {
        viewModelScope.launch {
            toggleFavoriteUseCase(breedImage)
        }
    }

}