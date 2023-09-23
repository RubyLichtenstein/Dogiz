package com.rubylichtenstein.ui.favorites

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rubylichtenstein.domain.common.capitalizeWords
import com.rubylichtenstein.ui.images.DogImagesGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteImages by viewModel.favoriteImagesState.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val selectedBreeds by viewModel.selectedBreeds.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Favorites",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            com.rubylichtenstein.ui.common.AsyncStateHandler(favoriteImages) { favoriteImages ->
                val breeds = favoriteImages.map { it.breedName }.distinct()

                if (favoriteImages.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No favorites yet")
                    }
                } else {
                    Column {
                        BreedFilter(breeds, selectedBreeds, viewModel::toggleSelectedBreed)
                        val filteredImages = if (selectedBreeds.isEmpty()) {
                            favoriteImages
                        } else {
                            favoriteImages.filter { it.breedName in selectedBreeds }
                        }

                        DogImagesGrid(
                            images = filteredImages,
                            onToggleFavorite = { dogImage -> viewModel.toggleFavorite(dogImage) },
                            showNames = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BreedFilter(
    breeds: List<String>,
    selectedBreeds: Set<String>,
    onToggleSelectedBreed: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        breeds.forEach { breed ->
            FilterChip(
                selected = selectedBreeds.contains(breed),
                onClick = { onToggleSelectedBreed(breed) },
                label = { Text(breed.capitalizeWords()) },
                trailingIcon = {
                    if (selectedBreeds.contains(breed)) {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}