package ru.otus.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val date: LocalDate,
    val temperature: Double,
    val description: String
)