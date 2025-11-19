package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.comp

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbIridescent
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.panabuntu.weathertracker.core.presentation.comp.ImageFromUrl
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.theme.LocalAppDimens
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.ForecastDetailInfo
import com.panabuntu.weathertracker.forecast_list.presentation.R

@Composable
fun ForecastContent(
    forecast: ForecastDetailInfo,
    modifier: Modifier = Modifier
) {
    val dimens = LocalAppDimens.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(dimens.paddingLarge.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "${forecast.dayName.asString()}  ${forecast.dayOfMonth} ${forecast.monthName.asString()}",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(dimens.paddingLarge.dp))

        ImageFromUrl(
            modifier = Modifier.size(128.dp),
            imageUrl = forecast.iconUrl,
            contentDescription = forecast.description,
        )

        Spacer(modifier = Modifier.height(dimens.paddingLarge.dp))

        Text(
            text = forecast.description ?: "",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(dimens.paddingLarge.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(dimens.forecastIcon.dp),
                imageVector = Icons.Default.Thermostat,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(dimens.paddingSmall.dp))
            Text(
                text = stringResource(R.string.forecast_daily_max_value, forecast.maxTemp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(dimens.paddingLarge.dp))
            Text(
                text = stringResource(R.string.forecast_daily_min_value, forecast.minTemp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(dimens.paddingHuge.dp))

        // Weather Details
        Column(verticalArrangement = Arrangement.spacedBy(dimens.paddingMedium.dp)) {
            ForecastValueRow(
                imageVector = Icons.Default.Air,
                label = stringResource(R.string.forecast_daily_wind_speed),
                value = forecast.windSpeed.asString()
            )
            ForecastValueRow(
                imageVector = Icons.Default.WaterDrop,
                label = stringResource(R.string.forecast_daily_humidity),
                value = forecast.humidity
            )
            ForecastValueRow(
                imageVector = Icons.Default.WbSunny,
                label = stringResource(R.string.forecast_daily_sunrise),
                value = forecast.sunrise
            )
            ForecastValueRow(
                imageVector = Icons.Default.NightsStay,
                label = stringResource(R.string.forecast_daily_sunset),
                value = forecast.sunset
            )
            ForecastValueRow(
                imageVector = Icons.Default.WbIridescent,
                label = stringResource(R.string.forecast_daily_uv_index),
                value = forecast.uvIndex
            )
            ForecastValueRow(
                imageVector = Icons.Default.Umbrella,
                label = stringResource(R.string.forecast_daily_rain_possibility),
                value = forecast.rainProbability
            )
        }
    }
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
private fun ForecastDayContentPreview() {

    val info = ForecastDetailInfo(
        dayName = UiText.DynamicString("Today"),
        iconUrl = "",
        minTemp = "12°",
        maxTemp = "23°",
        monthName = UiText.DynamicString("Jan"),
        windSpeed = UiText.DynamicString("12 km/h"),
        sunrise = "12:00",
        sunset = "12:00",
        humidity = "12%",
        uvIndex = "12",
        rainProbability = "12%",
        description = "Clear Sky",
        dayOfMonth = 23
    )

    AppTheme {
        ForecastContent(
            forecast = info,
            modifier = Modifier.fillMaxSize()
        )
    }
}
