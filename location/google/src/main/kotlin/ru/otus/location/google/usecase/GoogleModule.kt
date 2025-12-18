package ru.otus.location.google.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.otus.domain.usecase.LocationProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class GoogleModule {
    @Binds
    abstract fun locationProvider(impl: GoogleLocationProvider): LocationProvider
}