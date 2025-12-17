package ru.otus.cleanarchitecture.entity

import kotlinx.serialization.Serializable

@Serializable
data class City(val name: String, override val location: Location): WithLocation