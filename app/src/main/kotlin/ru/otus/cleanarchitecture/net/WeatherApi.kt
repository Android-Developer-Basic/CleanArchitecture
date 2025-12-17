package ru.otus.cleanarchitecture.net

import retrofit2.http.GET
import retrofit2.http.Query
import ru.otus.cleanarchitecture.entity.CityForecast

interface WeatherApi {
    @GET("cityForecast")
    suspend fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): CityForecast
}