package com.rubylichtenstein.ui.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubylichtenstein.domain.breeds.GetBreedsUseCase
import com.rubylichtenstein.ui.common.UiState
import com.rubylichtenstein.ui.common.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    getBreedsUseCase: GetBreedsUseCase
) : ViewModel() {

    val breedsState = getBreedsUseCase()
        .asUiState()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            UiState.Loading
        )
}
