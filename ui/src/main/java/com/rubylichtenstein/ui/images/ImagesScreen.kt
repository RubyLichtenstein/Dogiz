@file:OptIn(ExperimentalMaterial3Api::class)

package com.rubylichtenstein.ui.images

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rubylichtenstein.ui.common.AsyncStateHandler
import com.rubylichtenstein.domain.common.capitalizeWords
import com.rubylichtenstein.ui.favorites.FavoritesViewModel
import com.rubylichtenstein.domain.breeds.buildDisplayName

@Composable
fun ImagesScreen(
    navController: NavController,
    breed: String,
    subBreed: String?
) {
    val imagesViewModel: ImagesViewModel = hiltViewModel()
    val favoritesViewModel: com.rubylichtenstein.ui.favorites.FavoritesViewModel = hiltViewModel()
    val dogImages by imagesViewModel.dogImagesState.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(key1 = breed) {
        imagesViewModel.fetchDogImages(breed, subBreed)
    }

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
                        buildDisplayName(breed, subBreed).capitalizeWords(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "ArrowBack",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            com.rubylichtenstein.ui.common.AsyncStateHandler(dogImages) { dogImageList ->
                DogImagesGrid(
                    images = dogImageList,
                    onToggleFavorite = { dogImage -> favoritesViewModel.toggleFavorite(dogImage) },
                    showNames = false
                )
            }
        }
    }
}



