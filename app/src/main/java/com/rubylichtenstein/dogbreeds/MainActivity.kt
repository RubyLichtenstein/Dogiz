package com.rubylichtenstein.dogbreeds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import com.rubylichtenstein.dogbreeds.ui.theme.DogBreedsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavigator()
                }
            }
        }
    }
}