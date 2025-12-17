package ru.otus.entity

import kotlinx.serialization.Serializable

@Serializable
data class City(val name: String, override val location: Location): WithLocation