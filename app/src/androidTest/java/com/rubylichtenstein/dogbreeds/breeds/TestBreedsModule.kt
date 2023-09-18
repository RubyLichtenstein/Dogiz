package com.rubylichtenstein.dogbreeds.breeds

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.rubylichtenstein.dogbreeds.data.images.BreedImagesApi
import com.rubylichtenstein.dogbreeds.data.breeds.BreedsDataStore
import com.rubylichtenstein.dogbreeds.di.AppModule
import io.mockk.mockk

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestBreedsModule {

    @Provides
    fun provideBreedsApi(): BreedImagesApi = mockk()

    @Provides
    fun provideBreedsDataStore(): BreedsDataStore = mockk()
}