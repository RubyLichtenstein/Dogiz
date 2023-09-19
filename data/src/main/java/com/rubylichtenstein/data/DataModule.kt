package com.rubylichtenstein.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rubylichtenstein.data.breeds.BreedsDataStore
import com.rubylichtenstein.data.breeds.BreedsRemoteApi
import com.rubylichtenstein.data.breeds.BreedsRepositoryImpl
import com.rubylichtenstein.data.favorites.FavoritesRepositoryImpl
import com.rubylichtenstein.data.images.AppDatabase
import com.rubylichtenstein.data.images.BreedImagesApi
import com.rubylichtenstein.data.images.DogImageDao
import com.rubylichtenstein.data.images.ImagesRepositoryImpl
import com.rubylichtenstein.domain.breeds.data.BreedsRepository
import com.rubylichtenstein.domain.favorites.FavoritesRepository
import com.rubylichtenstein.domain.images.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private val Context.dataStore by preferencesDataStore("dog_breeds")

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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideDogImageDao(database: AppDatabase): DogImageDao {
        return database.dogImageDao()
    }

    @Provides
    @Singleton
    fun provideBreedsRepository(
        dogBreedApiService: BreedsRemoteApi,
        breedsDataStore: BreedsDataStore
    ): BreedsRepository {
        return BreedsRepositoryImpl(
            dogBreedApiService,
            breedsDataStore
        )
    }

    @Provides
    @Singleton
    fun provideBreedImagesRepository(
        dogBreedApiService: BreedImagesApi,
        dogImageDao: DogImageDao
    ): ImagesRepository {
        return ImagesRepositoryImpl(
            dogBreedApiService,
            dogImageDao
        )
    }
}