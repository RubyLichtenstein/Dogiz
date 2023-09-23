package com.rubylichtenstein.ui.images

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rubylichtenstein.domain.images.DogImageEntity

@Composable
fun DogImagesGrid(
    images: List<DogImageEntity>,
    onToggleFavorite: (DogImageEntity) -> Unit,
    showNames: Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
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
            DogImage(breedImage.url)
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
                    style = MaterialTheme.typography.bodyLarge,
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