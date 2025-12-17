package ru.otus.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import ru.otus.domain.repository.CityWeatherRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun cityWeatherRepository(impl: CityWeatherRepositoryImpl): CityWeatherRepository
}