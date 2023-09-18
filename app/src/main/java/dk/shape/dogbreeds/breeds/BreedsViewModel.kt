package dk.shape.dogbreeds.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.shape.domain.breeds.GetBreedsUseCase
import dk.shape.domain.common.AsyncResult
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
