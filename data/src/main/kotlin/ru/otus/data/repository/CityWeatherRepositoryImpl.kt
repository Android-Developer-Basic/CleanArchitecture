package ru.otus.data.repository

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import ru.otus.data.net.WeatherApi
import ru.otus.domain.lce.LceState
import ru.otus.domain.repository.CityWeatherRepository
import ru.otus.domain.usecase.LocationProvider
import ru.otus.entity.CityForecast
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val locationProvider: LocationProvider
) : CityWeatherRepository {
    private val _state = MutableStateFlow<LceState<CityForecast, Throwable>>(LceState.Loading(null))
    override val state: Flow<LceState<CityForecast, Throwable>> get() = _state.asStateFlow()

    private fun loadWeather() = flow {
        emit(LceState.Loading(_state.value.data))
        try {
            val location = locationProvider.getCurrentLocation()
            val weather = weatherApi.getWeather(location.lat, location.lon)
            emit(LceState.Content(weather))
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            emit(LceState.Error(e, _state.value.data))
        }
    }

    override suspend fun refresh() {
        loadWeather().collect(_state)
    }
}