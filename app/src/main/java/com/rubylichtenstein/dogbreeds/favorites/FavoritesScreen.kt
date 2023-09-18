package com.rubylichtenstein.dogbreeds.favorites

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.rubylichtenstein.dogbreeds.common.AsyncStateHandler
import com.rubylichtenstein.domain.common.capitalizeWords
import com.rubylichtenstein.dogbreeds.ui.images.DogImagesGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteImages by viewModel.favoriteImagesState.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val selectedBreeds = remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
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
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AsyncStateHandler(favoriteImages) { favoriteImages ->
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
                        BreedFilter(breeds, selectedBreeds)
                        val filteredImages = if (selectedBreeds.value.isEmpty()) {
                            favoriteImages
                        } else {
                            favoriteImages.filter { it.breedName in selectedBreeds.value }
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
    selectedBreeds: MutableState<Set<String>>
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        breeds.forEach { breed ->
            FilterChip(
                selected = selectedBreeds.value.contains(breed),
                onClick = {
                    val newSelectedBreeds = selectedBreeds.value.toMutableSet()
                    if (newSelectedBreeds.contains(breed)) {
                        newSelectedBreeds.remove(breed)
                    } else {
                        newSelectedBreeds.add(breed)
                    }
                    selectedBreeds.value = newSelectedBreeds
                },
                label = { Text(breed.capitalizeWords()) },
                trailingIcon = {
                    if (selectedBreeds.value.contains(breed)) {
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}