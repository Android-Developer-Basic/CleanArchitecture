package ru.otus.cleanarchitecture.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.otus.cleanarchitecture.R
import ru.otus.cleanarchitecture.ui.theme.AppDimens
import ru.otus.cleanarchitecture.ui.theme.CleanArchitectureTheme
import ru.otus.domain.lce.LceState
import ru.otus.entity.City
import ru.otus.entity.CityForecast
import ru.otus.entity.Location
import ru.otus.entity.Weather

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    forecastState: LceState<CityForecast, Throwable>,
    onRefreshClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_weather)) },
                actions = {
                    IconButton(
                        onClick = onRefreshClick,
                        enabled = forecastState is LceState.Content
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.btn_refresh))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (forecastState) {
                is LceState.Content -> {
                    ForecastView(forecastData = forecastState.data)
                }
                is LceState.Loading -> {
                    val data = forecastState.data
                    if (data != null) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            ForecastView(forecastData = data)
                        }
                    } else {
                        Loading(modifier = Modifier.fillMaxSize())
                    }
                }
                is LceState.Error -> {
                    val data = forecastState.data
                    if (data != null) {
                        ForecastView(forecastData = data)
                        LaunchedEffect(forecastState.error) {
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = forecastState.error.message ?: "Unknown error",
                                    actionLabel = "Retry"
                                )
                                if (result == androidx.compose.material3.SnackbarResult.ActionPerformed) {
                                    onRetryClick()
                                }
                            }
                        }
                    } else {
                        Error(
                            errorMessage = forecastState.error.message,
                            onRetryClick = onRetryClick,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastView(forecastData: CityForecast, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppDimens.margin_all)
    ) {
        Text(
            text = forecastData.city.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppDimens.spacer),
            style = MaterialTheme.typography.headlineMedium
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppDimens.spacer_small)
        ) {
            items(items = forecastData.forecast, key = { dailyWeather -> dailyWeather.date }) { dailyWeather ->
                ForecastItem(cityWeather = dailyWeather)
            }
        }
    }
}

@Composable
fun ForecastItem(cityWeather: Weather, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppDimens.vertical_margin_small),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = cityWeather.date.toString())
        Text(text = cityWeather.description)
        Text(text = "${cityWeather.temperature}°C")
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastViewPreview() {
    CleanArchitectureTheme {
        val sampleCityForecast = CityForecast(
            city = City("Moscow", Location(55.7558, 37.6176)),
            forecast = listOf(
                Weather(LocalDate(2024, 7, 20),temperature = 25.0, description = "Sunny"),
                Weather(date = LocalDate(2024, 7, 21),temperature = 22.5, description = "Cloudy"),
                Weather(date = LocalDate(2024, 7, 22), temperature = 20.0, description = "Rainy"),
                Weather(date = LocalDate(2024, 7, 23),temperature = 18.5, description = "Windy"),
                Weather(date = LocalDate(2024, 7, 24),temperature = 21.3, description = "Partly Cloudy")
            )
        )
        ForecastScreen(
            forecastState = LceState.Content(sampleCityForecast),
            onRefreshClick = {},
            onRetryClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastScreenLoadingPreview() {
    CleanArchitectureTheme {
        val sampleCityForecast = CityForecast(
            city = City("London", Location(51.5, 0.1)),
            forecast = listOf(
                Weather(LocalDate(2024, 7, 20),temperature = 15.0, description = "Cloudy")
            )
        )
        ForecastScreen(
            forecastState = LceState.Loading(sampleCityForecast),
            onRefreshClick = {},
            onRetryClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastScreenErrorPreview() {
    CleanArchitectureTheme {
        val sampleCityForecast = CityForecast(
            city = City("Paris", Location(48.8, 2.3)),
            forecast = listOf(
                Weather(LocalDate(2024, 7, 20),temperature = 20.0, description = "Sunny")
            )
        )
        ForecastScreen(
            forecastState = LceState.Error(RuntimeException("Failed to load forecast"), sampleCityForecast),
            onRefreshClick = {},
            onRetryClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastScreenEmptyLoadingPreview() {
    CleanArchitectureTheme {
        ForecastScreen(
            forecastState = LceState.Loading(null),
            onRefreshClick = {},
            onRetryClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastScreenEmptyErrorPreview() {
    CleanArchitectureTheme {
        ForecastScreen(
            forecastState = LceState.Error(RuntimeException("Network unavailable"), null),
            onRefreshClick = {},
            onRetryClick = {}
        )
    }
}
