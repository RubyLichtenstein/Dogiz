package com.rubylichtenstein.dogbreeds.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.rubylichtenstein.dogbreeds.data.images.BreedImagesApi
import com.rubylichtenstein.dogbreeds.data.MyHttpClient
import com.rubylichtenstein.dogbreeds.data.breeds.BreedsDataStore
import com.rubylichtenstein.dogbreeds.data.breeds.BreedsRemoteApi
import com.rubylichtenstein.dogbreeds.data.breeds.BreedsRepositoryImpl
import com.rubylichtenstein.dogbreeds.data.favorites.FavoritesRepositoryImpl
import com.rubylichtenstein.dogbreeds.data.images.AppDatabase
import com.rubylichtenstein.dogbreeds.data.images.DogImageDao
import com.rubylichtenstein.dogbreeds.data.images.ImagesRepositoryImpl
import com.rubylichtenstein.domain.breeds.data.BreedsRepository
import com.rubylichtenstein.domain.favorites.FavoritesRepository
import com.rubylichtenstein.domain.images.ImagesRepository
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
    fun provideFavoritesRepository(dogImageDao: DogImageDao): FavoritesRepository {
        return FavoritesRepositoryImpl(dogImageDao)
    }

    @Provides
    @Singleton
    fun provideBreedsDataStore(dataStore: DataStore<Preferences>): BreedsDataStore {
        return BreedsDataStore(dataStore)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideDogImageDao(database: AppDatabase): DogImageDao {
        return database.dogImageDao()
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
        dogImageDao: DogImageDao
    ): ImagesRepository {
        return ImagesRepositoryImpl(dogBreedApiService, dogImageDao)
    }
}