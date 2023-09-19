package com.rubylichtenstein.ui.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.rubylichtenstein.domain.breeds.GetBreedsUseCase
import com.rubylichtenstein.domain.common.AsyncResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    getBreedsUseCase: GetBreedsUseCase
) : ViewModel() {

    val breedsState = getBreedsUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            AsyncResult.Loading
        )
}
