package com.rubylichtenstein.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.images.DogImageEntity
import kotlinx.coroutines.flow.Flow

data class FilterChipInfo(
    val label: String,
    val selected: Boolean,
)

data class FavoritesModel(
    val dogImages: List<DogImageEntity>,
    val filterChipsInfo: Set<FilterChipInfo>,
)

sealed interface Event {
    data class ToggleSelectedBreed(val breed: FilterChipInfo) : Event
}

@Composable
fun FavoritesPresenter(
    events: Flow<Event>,
    favoriteImagesFlow: Flow<AsyncResult<List<DogImageEntity>>>,
): AsyncResult<FavoritesModel> {
    var favoriteImages by remember {
        mutableStateOf<AsyncResult<List<DogImageEntity>>>(AsyncResult.Loading)
    }

    var filterChips by remember {
        mutableStateOf<Set<FilterChipInfo>>(emptySet())
    }

    LaunchedEffect(Unit) {
        favoriteImagesFlow.collect {
            favoriteImages = it
            if (it is AsyncResult.Success<List<DogImageEntity>>) {
                val currentSelectedBreeds = filterChips
                    .filter { it.selected }
                    .map { it.label }
                    .toSet()

                filterChips = buildFilterChipsInfo(it.data, currentSelectedBreeds)
            }
        }
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is Event.ToggleSelectedBreed -> {
                    val toggleBreed = event.breed
                    filterChips = filterChips.map {
                        if (it.label == toggleBreed.label) {
                            it.copy(selected = !toggleBreed.selected)
                        } else {
                            it
                        }
                    }.toSet()
                }
            }
        }
    }

    return when (val images = favoriteImages) {
        is AsyncResult.Success -> {

            val selectedBreeds = filterChips
                .filter { it.selected }
                .map { it.label }
                .toSet()

            val filteredImages = images.data.filter {
                selectedBreeds.isEmpty() || it.breedName in selectedBreeds
            }

            AsyncResult.Success(
                FavoritesModel(
                    dogImages = filteredImages,
                    filterChipsInfo = filterChips
                )
            )
        }

        is AsyncResult.Error -> AsyncResult.Error()
        else -> AsyncResult.Loading
    }
}

fun buildFilterChipsInfo(
    dogImages: List<DogImageEntity>,
    selectedBreeds: Set<String>
): Set<FilterChipInfo> {
    return dogImages
        .map { it.breedName }
        .distinct()
        .map { name ->
            FilterChipInfo(
                label = name,
                selected = name in selectedBreeds
            )
        }.toSet()
}
