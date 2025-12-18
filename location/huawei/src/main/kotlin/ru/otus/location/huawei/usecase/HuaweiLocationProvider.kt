package ru.otus.location.huawei.usecase

import android.util.Log
import kotlinx.coroutines.delay
import ru.otus.domain.usecase.LocationProvider
import ru.otus.entity.Location
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class HuaweiLocationProvider @Inject constructor() : LocationProvider {
    override suspend fun getCurrentLocation(): Location {
        Log.i("HUAWEI", "Providing location...")
        delay(1.seconds)
        return Location(55.7962, 49.1222)
    }
}