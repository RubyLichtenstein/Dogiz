package com.rubylichtenstein.ui.images

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

@Composable
fun DogImage(url: String) {
    SubcomposeAsyncImage(
        model = url,
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