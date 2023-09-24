package com.rubylichtenstein.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rubylichtenstein.ui.breeds.BreedsScreen
import com.rubylichtenstein.ui.favorites.FavoriteCountBadge
import com.rubylichtenstein.ui.favorites.FavoritesScreen
import com.rubylichtenstein.ui.images.ImagesScreen
import com.rubylichtenstein.ui.theme.DogBreedsTheme


sealed class Screen(val route: String) {
    data object BreedsList : Screen("breedsList")
    data object Favorites : Screen("favorites")
    class DogImages(breed: String) : Screen("dogImages/$breed")
}

@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.BreedsList.route) {
        composable(route = Screen.BreedsList.route) {
            BreedsScreen(navController = navController)
        }

        /**
         * A Composable function responsible for navigating to and displaying images of a dog breed or sub-breed.
         *
         * The navigation argument 'breed' should be passed in the format "breed" or "breed_subBreed".
         * For example, to view images of the Bulldog breed, the argument should be "bulldog".
         * To view images of the Australian Shepherd sub-breed, the argument should be "shepherd_australian".
         *
         */
        composable(
            route = "dogImages/{breed}",
            arguments = listOf(navArgument("breed") { type = NavType.StringType })
        ) { backStackEntry ->
            val breedArg = backStackEntry.arguments?.getString("breed")
            if (breedArg != null) {
                // Split the 'breedArg' to separate the breed and sub-breed, if applicable.
                val (breed, subBreed) = breedArg.split('_').let {
                    it[0] to it.getOrNull(1)
                }

                ImagesScreen(
                    navController = navController,
                    breed = breed,
                    subBreed = subBreed
                )
            }
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(navController)
        }
    }
}

@Composable
fun AppNavigator() {
    val navController: NavHostController = rememberNavController()

    DogBreedsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            MainAppScreen(navController = navController)
        }
    }
}


@Composable
fun MainAppScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    NavigationBarItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Breeds") },
                        selected = currentRoute == Screen.BreedsList.route,
                        onClick = {
                            navController.navigate(Screen.BreedsList.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    NavigationBarItem(
                        icon = {
                            FavoriteCountBadge()
                        },
                        label = { Text("Favorites") },
                        selected = currentRoute == Screen.Favorites.route,
                        onClick = {
                            navController.navigate(Screen.Favorites.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            MyNavHost(navController)
        }
    }
}

