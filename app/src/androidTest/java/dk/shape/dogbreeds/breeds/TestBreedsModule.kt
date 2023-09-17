package dk.shape.dogbreeds.breeds

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dk.shape.dogbreeds.api.BreedsApi
import dk.shape.dogbreeds.di.AppModule
import io.mockk.mockk

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestBreedsModule {

    @Provides
    fun provideBreedsApi(): BreedsApi = mockk()

    @Provides
    fun provideBreedsDataStore(): BreedsDataStore = mockk()
}