@file:OptIn(ExperimentalMaterial3Api::class)

package dk.shape.dogbreeds.images

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dk.shape.dogbreeds.common.AsyncStateHandler
import dk.shape.domain.common.capitalizeWords
import dk.shape.dogbreeds.favorites.FavoritesViewModel
import dk.shape.domain.breeds.BreedItem.Companion.buildDisplayName

@Composable
fun ImagesScreen(
    navController: NavController,
    breed: String,
    subBreed: String?
) {
    val imagesViewModel: ImagesViewModel = hiltViewModel()
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val dogImages by imagesViewModel.dogImagesState.collectAsState()
    val favoriteImages by favoritesViewModel.favoriteImages.collectAsState(initial = listOf())

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
            AsyncStateHandler(dogImages) { dogImageList ->
                DogImagesGrid(
                    images = dogImageList,
                    onToggleFavorite = { dogImage -> favoritesViewModel.toggleFavorite(dogImage) },
                    favoriteImages = favoriteImages,
                    showNames = false
                )
            }
        }
    }
}



