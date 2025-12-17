package ru.otus.domain.usecase

import ru.otus.entity.Location

interface LocationProvider {
    suspend fun getCurrentLocation(): Location
}