package com.rubylichtenstein.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import com.rubylichtenstein.domain.images.GetBreedImagesUseCase
import com.rubylichtenstein.domain.images.buildBreedKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesByBreedUseCase: GetBreedImagesUseCase,
) : ViewModel() {

    private val _dogImagesState =
        MutableStateFlow<AsyncResult<List<DogImageEntity>>>(AsyncResult.Loading)
    val dogImagesState: StateFlow<AsyncResult<List<DogImageEntity>>> get() = _dogImagesState

    fun fetchDogImages(breed: String, subBreed: String?) {
        val breedKey = buildBreedKey(subBreed, breed)

        viewModelScope.launch(Dispatchers.IO) {
            getImagesByBreedUseCase(breedKey)
                .collect { state -> _dogImagesState.value = state }
        }
    }
}

