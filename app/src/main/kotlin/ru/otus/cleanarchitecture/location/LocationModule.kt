package ru.otus.cleanarchitecture.location

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {
    @Provides
    @Singleton
    fun locationProvider(): LocationProvider = LocationProvider()
}