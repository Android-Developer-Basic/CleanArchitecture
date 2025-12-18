package ru.otus.location.google.usecase

import android.util.Log
import kotlinx.coroutines.delay
import ru.otus.domain.usecase.LocationProvider
import ru.otus.entity.Location
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class GoogleLocationProvider @Inject constructor() : LocationProvider {
    override suspend fun getCurrentLocation(): Location {
        Log.i("GOOGLE", "Providing location...")
        delay(1.seconds)
        return Location(55.7962, 49.1222)
    }
}