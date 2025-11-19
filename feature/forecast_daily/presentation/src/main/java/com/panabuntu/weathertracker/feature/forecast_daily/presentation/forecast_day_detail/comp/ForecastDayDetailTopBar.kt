package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.comp

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.util.balanced

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastDayDetailTopBar(
    modifier: Modifier = Modifier,
    locationName: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = locationName,
                style = MaterialTheme.typography.headlineLarge.balanced()
            )
        },
        actions = {
            // fake IconButton to center title
            IconButton(
                modifier = Modifier,
                onClick = onBackClick,
                enabled = false
            ) {}
        }
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun ForecastDayDetailTopBarPreview() {
    AppTheme {
        ForecastDayDetailTopBar(
            modifier = Modifier,
            locationName = "Madrid",
            onBackClick = {}
        )
    }
}