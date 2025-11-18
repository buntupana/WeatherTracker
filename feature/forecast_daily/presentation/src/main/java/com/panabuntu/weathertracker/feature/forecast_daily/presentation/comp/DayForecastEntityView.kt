package com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp

import com.panabuntu.weathertracker.core.presentation.util.UiText

data class DayForecastEntityView(
    val timestamp: Long,
    val iconUrl: String?,
    val day: UiText,
    val date: String,
    val maxTemp: String,
    val minTemp: String,
    val description: String?
)
