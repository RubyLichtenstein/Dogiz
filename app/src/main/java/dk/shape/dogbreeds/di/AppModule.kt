package dk.shape.dogbreeds.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dk.shape.dogbreeds.api.BreedsApi
import dk.shape.dogbreeds.api.MyHttpClient
import dk.shape.dogbreeds.breeds.BreedsDataStore
import dk.shape.dogbreeds.breeds.BreedsRepository
import dk.shape.dogbreeds.favorites.FavoritesRepository
import dk.shape.dogbreeds.images.ImagesDataStore
import dk.shape.dogbreeds.images.ImagesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val Context.dataStore by preferencesDataStore("dog_breeds")

    @Provides
    @Singleton
    fun provideBreedsApi(httpClient: MyHttpClient): BreedsApi = BreedsApi(httpClient)

    @Provides
    @Singleton
    fun provideHttpClient(): MyHttpClient = MyHttpClient()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(dataStore: DataStore<Preferences>): FavoritesRepository {
        return FavoritesRepository(dataStore)
    }

    @Provides
    @Singleton
    fun provideBreedsDataStore(dataStore: DataStore<Preferences>): BreedsDataStore {
        return BreedsDataStore(dataStore)
    }

    @Provides
    @Singleton
    fun provideBreedImagesDataStore(dataStore: DataStore<Preferences>): ImagesDataStore {
        return ImagesDataStore(dataStore)
    }

    @Provides
    @Singleton
    fun provideBreedsRepository(
        dogBreedApiService: BreedsApi,
        breedsDataStore: BreedsDataStore
    ): BreedsRepository {
        return BreedsRepository(dogBreedApiService, breedsDataStore)
    }

    @Provides
    @Singleton
    fun provideBreedImagesRepository(
        dogBreedApiService: BreedsApi,
        imagesDataStore: ImagesDataStore
    ): ImagesRepository {
        return ImagesRepository(dogBreedApiService, imagesDataStore)
    }
}