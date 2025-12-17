package ru.otus.entity

import kotlinx.serialization.Serializable

@Serializable
data class CityForecast(
    val city: City,
    val forecast: List<Weather>
)
