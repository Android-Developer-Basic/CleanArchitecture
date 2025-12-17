package ru.otus.cleanarchitecture.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.otus.cleanarchitecture.R
import ru.otus.cleanarchitecture.ui.theme.AppDimens

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Error(
    errorMessage: String?,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppDimens.margin_all),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = stringResource(R.string.error),
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .size(AppDimens.main_icon_size)
                .padding(bottom = AppDimens.spacer)
        )
        Text(
            text = errorMessage ?: stringResource(R.string.unknown_error),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = AppDimens.spacer)
        )
        Spacer(modifier = Modifier.height(AppDimens.spacer))
        Button(
            onClick = onRetryClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorScreen() {
    Error(
        errorMessage = "Something went wrong!",
        onRetryClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorScreenNullMessage() {
    Error(
        errorMessage = null,
        onRetryClick = {}
    )
}
