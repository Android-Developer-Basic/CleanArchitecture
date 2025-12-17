package ru.otus.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.otus.domain.lce.LceState
import ru.otus.entity.CityForecast

interface CityWeatherRepository {
    val state: Flow<LceState<CityForecast, Throwable>>

    suspend fun refresh()
}