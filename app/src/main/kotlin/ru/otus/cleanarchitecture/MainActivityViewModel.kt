package ru.otus.cleanarchitecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.otus.domain.lce.LceState
import ru.otus.domain.repository.CityWeatherRepository
import ru.otus.entity.CityForecast
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val cityWeatherRepository: CityWeatherRepository) : ViewModel() {
    private var _refresh: Job? = null

    init { refresh() }

    val state: StateFlow<LceState<CityForecast, Throwable>> = cityWeatherRepository.state.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        LceState.Loading(null)
    )

    fun refresh() {
        _refresh?.cancel()
        _refresh = viewModelScope.launch { cityWeatherRepository.refresh() }
    }
}