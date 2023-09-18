package com.rubylichtenstein.dogbreeds.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.favorites.FavoritesRepository
import com.rubylichtenstein.domain.favorites.GetFavoriteImagesUseCase
import com.rubylichtenstein.domain.favorites.ToggleFavoriteUseCase
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.MutableStateFlow
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
) : ViewModel() {

    private val _favoriteImagesState =
        MutableStateFlow<AsyncResult<List<DogImageEntity>>>(AsyncResult.Loading)
    val favoriteImagesState: StateFlow<AsyncResult<List<DogImageEntity>>> get() = _favoriteImagesState

    init {
        viewModelScope.launch {
            getFavoriteImagesUseCase().collect { asyncState ->
                _favoriteImagesState.value = asyncState
            }
        }
    }

    val favoriteCount: StateFlow<Int> = favoriteImagesState.map {
        when (it) {
            is AsyncResult.Success -> it.data.size
            else -> 0
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    val favoriteImages: StateFlow<List<DogImageEntity>> = favoriteImagesState.map {
        when (it) {
            is AsyncResult.Success -> it.data
            else -> emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun toggleFavorite(breedImage: DogImageEntity) {
        viewModelScope.launch {
            toggleFavoriteUseCase(breedImage)
        }
    }
}