package com.rubylichtenstein.dogbreeds.data.favorites

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rubylichtenstein.domain.common.AsyncResult
import com.rubylichtenstein.domain.common.asResult
import com.rubylichtenstein.domain.favorites.FavoritesRepository
import com.rubylichtenstein.domain.images.DogImageData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : FavoritesRepository {

    private val favoriteImagesKey = stringPreferencesKey("favorite_images")

    override val favoriteImagesFlow: Flow<AsyncResult<List<DogImageData>>> =
        dataStore.data.map { preferences ->
            val json = preferences[favoriteImagesKey] ?: ""
            if (json.isEmpty()) {
                emptyList()
            } else {
                val decodedList = Json.decodeFromString<List<DogImageData>>(json)
                decodedList
            }
        }.asResult()

    override suspend fun toggleFavorite(breedImage: DogImageData): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            val json = preferences[favoriteImagesKey] ?: ""
            val currentFavorites = if (json.isEmpty()) {
                mutableListOf()
            } else {
                Json.decodeFromString<MutableList<DogImageData>>(json)
            }

            if (currentFavorites.contains(breedImage)) {
                currentFavorites.remove(breedImage)
            } else {
                currentFavorites.add(breedImage)
            }

            val updatedJson = Json.encodeToString(currentFavorites)
            preferences[favoriteImagesKey] = updatedJson
        }
    }
}


