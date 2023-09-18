@file:OptIn(ExperimentalMaterial3Api::class)

package com.rubylichtenstein.dogbreeds.breeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LargeTopAppBar
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
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.dogbreeds.common.AsyncStateHandler
import com.rubylichtenstein.domain.breeds.BreedEntity
import com.rubylichtenstein.domain.common.capitalizeWords

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
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Dog Breeds",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
            BreedListItem(breed = breed.name, onClick = { onItemClick(breed) })
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

@Composable
fun BreedListItem(breed: String, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(top = 4.dp, bottom = 4.dp)
            .fillMaxWidth(),
        headlineContent = {
            Text(
                text = breed.capitalizeWords(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
    )
}