package dk.shape.dogbreeds.images

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dk.shape.dogbreeds.model.BreedImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun save(images: List<BreedImage>, breed: String) {
        val key = stringPreferencesKey(breed)
        val json = Json.encodeToString(images)
        dataStore.edit { preferences ->
            preferences[key] = json
        }
    }

    fun getByBreed(breed: String): Flow<List<BreedImage>?> {
        val key = stringPreferencesKey(breed)
        return dataStore.data.map { preferences ->
            preferences[key]?.let { json ->
                Json.decodeFromString<List<BreedImage>>(json)
            }
        }
    }
}