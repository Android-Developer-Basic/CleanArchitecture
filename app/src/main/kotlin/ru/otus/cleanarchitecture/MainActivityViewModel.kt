package ru.otus.cleanarchitecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.otus.cleanarchitecture.lce.LceState
import ru.otus.cleanarchitecture.location.LocationProvider
import ru.otus.cleanarchitecture.net.WeatherApi
import ru.otus.entity.CityForecast
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherApi: WeatherApi,
    private val locationProvider: LocationProvider
) : ViewModel() {
    private val _state = MutableStateFlow<LceState<CityForecast, Throwable>>(LceState.Loading(null))
    private var _refresh: Job? = null

    init { loadWeather() }

    private fun loadWeather() = viewModelScope.launch {
        _state.value = LceState.Loading(_state.value.data)
        try {
            val location = locationProvider.getCurrentLocation()
            val weather = weatherApi.getWeather(location.lat, location.lon)
            _state.value = LceState.Content(weather)
        } catch (e: Exception) {
            ensureActive()
            _state.value = LceState.Error(e, _state.value.data)
        }
    }

    val state: StateFlow<LceState<CityForecast, Throwable>> get() = _state.asStateFlow()
    fun refresh() {
        _refresh?.cancel()
        _refresh = viewModelScope.launch { loadWeather() }
    }
}