package ru.otus.cleanarchitecture.entity

import kotlinx.datetime.LocalDate

val sampleNovosibirskForecast = CityForecast(
    city = City("Novosibirsk", Location(55.0395, 82.8950)),
    forecast = listOf(
        Weather(LocalDate(2025, 12, 27), -12.0, "Snowy"),
        Weather(LocalDate(2025, 12, 28), -15.5, "Cloudy with snow"),
        Weather(LocalDate(2025, 12, 29), -18.0, "Clear and frosty"),
        Weather(LocalDate(2025, 12, 30), -20.0, "Heavy snow"),
        Weather(LocalDate(2025, 12, 31), -22.5, "New Year's Eve, very cold"),
        Weather(LocalDate(2026, 1, 1), -21.0, "New Year's Day, snowy"),
        Weather(LocalDate(2026, 1, 2), -19.0, "Icy conditions"),
        Weather(LocalDate(2026, 1, 3), -16.0, "Partly cloudy"),
        Weather(LocalDate(2026, 1, 4), -13.5, "Light snow"),
        Weather(LocalDate(2026, 1, 5), -11.0, "Overcast")
    )
)