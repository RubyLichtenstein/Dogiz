@file:OptIn(ExperimentalMaterial3Api::class)

package dk.shape.dogbreeds.breeds

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dk.shape.domain.common.AsyncResult
import dk.shape.dogbreeds.common.AsyncStateHandler
import dk.shape.domain.common.capitalizeWords
import dk.shape.domain.breeds.BreedItem

@Composable
fun BreedsScreen(
    viewModel: BreedsViewModel = hiltViewModel(),
    navController: NavController
) {
    val breedListState by viewModel.breedsState.collectAsState()

    BreedsScreen(
        breedListState = breedListState,
        navigateToDogImages = { breedItem ->
            val breedRoute = when (breedItem) {
                is BreedItem.Breed -> breedItem.name
                is BreedItem.SubBreed -> "${breedItem.parentBreed}_${breedItem.name}"
            }
            navController.navigate("dogImages/$breedRoute")
        }
    )
}

@Composable
fun BreedsScreen(
    breedListState: AsyncResult<List<BreedItem>>,
    navigateToDogImages: (BreedItem) -> Unit
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
fun BreedList(breeds: List<BreedItem>, onItemClick: (BreedItem) -> Unit) {
    LazyColumn {
        items(breeds) { breed ->
            BreedListItem(breed = breed.breedName(), onClick = { onItemClick(breed) })
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

@Composable
@Preview(showBackground = true)
fun BreedsScreenPreview() {
    val mockData = listOf(
        BreedItem.Breed("Husky", listOf(BreedItem.SubBreed("Siberian", "Husky"))),
        BreedItem.Breed("Labrador", listOf(BreedItem.SubBreed("Chocolate", "Labrador"))),
        BreedItem.Breed("Poodle", emptyList())
    )

    val mockAsyncResult: AsyncResult<List<BreedItem>> = AsyncResult.Success(mockData)

    val mockNavigateToDogImages: (BreedItem) -> Unit = {}

    BreedsScreen(
        breedListState = mockAsyncResult,
        navigateToDogImages = mockNavigateToDogImages
    )
}