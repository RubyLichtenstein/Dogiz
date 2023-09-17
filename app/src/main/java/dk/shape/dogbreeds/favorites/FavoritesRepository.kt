package dk.shape.dogbreeds.favorites

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dk.shape.domain.common.AsyncState
import dk.shape.dogbreeds.model.BreedImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val favoriteImagesKey = stringPreferencesKey("favorite_images")

    val favoriteImagesFlow: Flow<AsyncState<List<BreedImage>>> = dataStore.data.map { preferences ->
        runCatching {
            val json = preferences[favoriteImagesKey] ?: ""
            if (json.isEmpty()) {
                AsyncState.Success(emptyList())
            } else {
                val decodedList = Json.decodeFromString<List<BreedImage>>(json)
                AsyncState.Success(decodedList)
            }
        }.getOrElse { e ->
            AsyncState.Error("An error occurred: ${e.message}")
        }
    }

    suspend fun toggleFavorite(breedImage: BreedImage) {
        runCatching {
            dataStore.edit { preferences ->
                val json = preferences[favoriteImagesKey] ?: ""
                val currentFavorites = if (json.isEmpty()) {
                    mutableListOf()
                } else {
                    Json.decodeFromString<MutableList<BreedImage>>(json)
                }

                if (currentFavorites.contains(breedImage)) {
                    currentFavorites.remove(breedImage)
                } else {
                    currentFavorites.add(breedImage)
                }

                val updatedJson = Json.encodeToString(currentFavorites)
                preferences[favoriteImagesKey] = updatedJson
            }
        }.getOrElse { e ->
            println("An error occurred while toggling favorite: ${e.message}")
        }
    }
}

