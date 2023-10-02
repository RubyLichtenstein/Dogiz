package com.rubylichtenstein.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.rubylichtenstein.domain.images.DogImageEntity
import com.rubylichtenstein.ui.common.AsyncResult
import com.rubylichtenstein.ui.common.asAsyncResult
import com.rubylichtenstein.ui.common.mapSuccess
import kotlinx.coroutines.flow.Flow

data class ChipInfo(
    val label: String,
    val selected: Boolean,
)

data class FavoritesModel(
    val dogImages: List<DogImageEntity>,
    val filterChipsInfo: Set<ChipInfo>,
)

sealed interface Event {
    data class ToggleSelectedBreed(val breed: ChipInfo) : Event
}

@Composable
fun FavoritesPresenter(
    events: Flow<Event>,
    favoriteImagesFlow: Flow<List<DogImageEntity>>
): AsyncResult<FavoritesModel> {
    var favoriteImages by remember {
        mutableStateOf<AsyncResult<List<DogImageEntity>>>(
            AsyncResult.Loading
        )
    }

    var filteredBreeds by remember {
        mutableStateOf(emptySet<String>())
    }

    LaunchedEffect(Unit) {
        favoriteImagesFlow
            .asAsyncResult()
            .collect { images ->
                favoriteImages = images
            }
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            if (event is Event.ToggleSelectedBreed) {
                val toggleBreed = event.breed
                filteredBreeds = if (toggleBreed.selected) {
                    filteredBreeds - toggleBreed.label
                } else {
                    filteredBreeds + toggleBreed.label
                }
            }
        }
    }

    val images = favoriteImages
    return images.mapSuccess {
        val filteredImages = it.filter {
            val selectedBreeds = filteredBreeds
            selectedBreeds.isEmpty() || it.breedName in selectedBreeds
        }

        val chipsLabels = it.map { it.breedName }.toSet()
        val filterChipsInfo = chipsLabels.map { label ->
            ChipInfo(
                label = label,
                selected = label in filteredBreeds
            )
        }.toSet()


        FavoritesModel(
            dogImages = filteredImages,
            filterChipsInfo = filterChipsInfo
        )
    }
}