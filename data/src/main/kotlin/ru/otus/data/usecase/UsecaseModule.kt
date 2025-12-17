package ru.otus.data.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.otus.domain.usecase.LocationProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class UsecaseModule {
    @Binds
    abstract fun locationProvider(impl: LocationProviderImpl): LocationProvider
}