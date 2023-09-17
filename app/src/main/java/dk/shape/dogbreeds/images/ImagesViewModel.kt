package dk.shape.dogbreeds.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository,
) : ViewModel() {
    init {
        imagesRepository.resetState()
    }

    val dogImagesState = imagesRepository.breedImagesState

    fun fetchDogImages(breed: String, subBreed: String?) {
        viewModelScope.launch {
            val breedKey = if (subBreed == null) breed else "$breed/$subBreed"
            imagesRepository.getImagesByBreed(breedKey, viewModelScope)
        }
    }
}

