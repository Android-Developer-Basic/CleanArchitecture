package ru.otus.cleanarchitecture.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class CityForecast(
    val city: City,
    val forecast: List<Weather>
)
