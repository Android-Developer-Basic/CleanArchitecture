package ru.otus.entity

import kotlinx.serialization.Serializable
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Location data
 */
@Serializable
data class Location(val lat: Double, val lon: Double)

/**
 * Entity with location
 */
interface WithLocation {
    val location: Location
}

/**
 * Calculates distance between two coordinates in km
 */
fun Location.distance(from: Location): Double {
    val (lat1, lon1) = this
    val (lat2, lon2) = from

    val r = 6371 // Radius of the earth

    val f1 = Math.toRadians(lat1)
    val l1 = Math.toRadians(lon1)
    val f2 = Math.toRadians(lat2)
    val l2 = Math.toRadians(lon2)

    val df = f2 - f1
    val dl = l2 - l1

    val a:Double = sin(df/2) * sin(df/2) + cos(f1) * cos(f2) * sin(dl/2) * sin(dl/2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return r * c
}

/**
 * Sorts collection by distance to user location
 */
fun Collection<WithLocation>.sortedByDistance(userLocation: Location): List<WithLocation> {
    return sortedBy { it.location.distance(userLocation) }
}