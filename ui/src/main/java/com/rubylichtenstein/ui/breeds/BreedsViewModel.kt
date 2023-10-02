package com.rubylichtenstein.ui.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubylichtenstein.domain.breeds.GetBreedsUseCase
import com.rubylichtenstein.ui.common.AsyncResult
import com.rubylichtenstein.ui.common.asAsyncResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    getBreedsUseCase: GetBreedsUseCase
) : ViewModel() {

    val breedsState = getBreedsUseCase()
        .asAsyncResult()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            AsyncResult.Loading
        )
}
