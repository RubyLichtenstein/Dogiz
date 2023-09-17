package dk.shape.dogbreeds.breeds

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dk.shape.dogbreeds.data.images.BreedImagesApi
import dk.shape.dogbreeds.data.breeds.BreedsDataStore
import dk.shape.dogbreeds.di.AppModule
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