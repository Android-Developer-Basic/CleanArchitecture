package ru.otus.fakedata.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import ru.otus.domain.lce.LceState
import ru.otus.domain.repository.CityWeatherRepository
import ru.otus.entity.CityForecast
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class CityWeatherRepositoryFakeImpl @Inject constructor() : CityWeatherRepository {
    private val _state = MutableStateFlow<LceState<CityForecast, Throwable>>(LceState.Loading(null))
    override val state: Flow<LceState<CityForecast, Throwable>> get() = _state.asStateFlow()

    private fun loadWeather() = flow {
        emit(LceState.Loading(_state.value.data))
        delay(1.seconds)
        emit(LceState.Content(sampleNovosibirskForecast))
    }

    override suspend fun refresh() {
        loadWeather().collect(_state)
    }
}