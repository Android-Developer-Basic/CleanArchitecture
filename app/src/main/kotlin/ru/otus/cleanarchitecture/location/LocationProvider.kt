package ru.otus.cleanarchitecture.location

import kotlinx.coroutines.delay
import ru.otus.entity.Location
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class LocationProvider @Inject constructor() {
    suspend fun getCurrentLocation(): Location {
        delay(1.seconds)
        return Location(55.7962, 49.1222)
    }
}
