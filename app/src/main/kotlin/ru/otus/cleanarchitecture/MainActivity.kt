package ru.otus.cleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.cleanarchitecture.ui.ForecastScreen
import ru.otus.cleanarchitecture.ui.theme.CleanArchitectureTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanArchitectureTheme {
                ForecastScreen(
                    forecastState = viewModel.state.collectAsStateWithLifecycle().value,
                    onRefreshClick = viewModel::refresh,
                    onRetryClick = viewModel::refresh,
                )
            }
        }
    }
}
