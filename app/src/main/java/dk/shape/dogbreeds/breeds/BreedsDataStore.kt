package dk.shape.dogbreeds.breeds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dk.shape.dogbreeds.model.BreedInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val dogBreedsKey = stringPreferencesKey("dog_breeds")

    val get: Flow<List<BreedInfo>?> = dataStore.data.map { preferences ->
        preferences[dogBreedsKey]?.let { json ->
            try {
                Json.decodeFromString<List<BreedInfo>>(json)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun save(breeds: List<BreedInfo>) {
        runCatching {
            val json = Json.encodeToString(breeds)
            dataStore.edit { preferences ->
                preferences[dogBreedsKey] = json
            }
        }.getOrElse { e ->

        }
    }
}
