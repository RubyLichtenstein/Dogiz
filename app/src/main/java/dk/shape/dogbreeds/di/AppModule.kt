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
import dk.shape.dogbreeds.data.images.BreedImagesApi
import dk.shape.dogbreeds.data.MyHttpClient
import dk.shape.dogbreeds.data.breeds.BreedsDataStore
import dk.shape.dogbreeds.data.breeds.BreedsRemoteApi
import dk.shape.dogbreeds.data.breeds.BreedsRepositoryImpl
import dk.shape.dogbreeds.data.favorites.FavoritesRepositoryImpl
import dk.shape.dogbreeds.data.images.ImagesDataStore
import dk.shape.dogbreeds.data.images.ImagesRepositoryImpl
import dk.shape.domain.breeds.BreedsRepository
import dk.shape.domain.favorites.FavoritesRepository
import dk.shape.domain.images.ImagesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val Context.dataStore by preferencesDataStore("dog_breeds")

    @Provides
    @Singleton
    fun provideBreedsApi(httpClient: MyHttpClient): BreedImagesApi = BreedImagesApi(httpClient)

    @Provides
    @Singleton
    fun provideBreedsRemoteApi(httpClient: MyHttpClient) = BreedsRemoteApi(httpClient)

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
        return FavoritesRepositoryImpl(dataStore)
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
        dogBreedApiService: BreedsRemoteApi,
        breedsDataStore: BreedsDataStore
    ): BreedsRepository {
        return BreedsRepositoryImpl(dogBreedApiService, breedsDataStore)
    }

    @Provides
    @Singleton
    fun provideBreedImagesRepository(
        dogBreedApiService: BreedImagesApi,
        imagesDataStore: ImagesDataStore
    ): ImagesRepository {
        return ImagesRepositoryImpl(dogBreedApiService, imagesDataStore)
    }
}