@file:OptIn(ExperimentalMaterial3Api::class)

package com.rubylichtenstein.ui.breeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rubylichtenstein.domain.breeds.BreedEntity
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.capitalizeWords
import com.rubylichtenstein.ui.common.AsyncStateHandler

@Composable
fun BreedsScreen(
    viewModel: BreedsViewModel = hiltViewModel(),
    navController: NavController
) {
    val breedListState by viewModel.breedsState.collectAsStateWithLifecycle()

    BreedsScreen(
        breedListState = breedListState,
        navigateToDogImages = { breedItem ->
            navController.navigate("dogImages/${breedItem.route}")
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun BreedsScreen(
    breedListState: AsyncResult<List<BreedEntity>>,
    navigateToDogImages: (BreedEntity) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Dogiz",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AsyncStateHandler(breedListState) { data ->
                BreedList(
                    breeds = data,
                    onItemClick = navigateToDogImages
                )
            }
        }
    }
}

@Composable
fun BreedList(breeds: List<BreedEntity>, onItemClick: (BreedEntity) -> Unit) {
    LazyColumn {
        items(breeds) { breed ->
            BreedListItem(breed = breed, onClick = { onItemClick(breed) })
        }
    }
}

@Composable
fun BreedListItem(breed: BreedEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = breed.name.capitalizeWords(),
                )
            },
            Modifier.clickable(onClick = onClick),
            tonalElevation = 4.dp,
        )
    }
}