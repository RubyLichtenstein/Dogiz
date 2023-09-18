package com.rubylichtenstein.dogbreeds.ui.images

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.rubylichtenstein.domain.images.DogImageEntity

@Composable
fun DogImagesGrid(
    images: List<DogImageEntity>,
    onToggleFavorite: (DogImageEntity) -> Unit,
    showNames: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(images) { dogImage ->
            DogImageItem(
                breedImage = dogImage,
                onToggleFavorite = onToggleFavorite,
            )
        }
    }
}

@Composable
fun DogImageItem(
    breedImage: DogImageEntity,
    onToggleFavorite: (DogImageEntity) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxSize()
        ) {
            DogImage(breedImage)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = breedImage.breedName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                )

                FavoriteIconButton(
                    isFavorite = breedImage.isFavorite,
                    onToggleFavorite = { onToggleFavorite(breedImage) }
                )
            }
        }
    }
}


@Composable
private fun DogImage(breedImage: DogImageEntity) {
    SubcomposeAsyncImage(
        model = breedImage.url,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center  // <-- Centering here
            ) {
                CircularProgressIndicator(
                    Modifier.size(32.dp)
                )
            }
        },
        error = {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center  // <-- Centering here
            ) {
                Text(
                    text = "Image failed to load",
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                )
            }
        }
    )
}

@Composable
fun FavoriteIconButton(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (isFavorite) 1f else 0.5f, label = ""
    )

    val iconImage = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    IconButton(
        modifier = modifier,
        onClick = { onToggleFavorite() }
    ) {
        Icon(
            imageVector = iconImage,
            contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
            tint = MaterialTheme.colorScheme.primary.copy(alpha = animatedProgress)
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewDogImagesGrid() {
//    val mockImages = List(50) {
////        DogImageEntity(
////            breedKey = "breedKey",
////            url = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
////        )
//    }
//
//    val mockFavorites = listOf<DogImageEntity>()
//
//    DogImagesGrid(
//        images = mockImages,
//        onToggleFavorite = {},
//        favoriteImages = mockFavorites,
//        showNames = true
//    )
//}