package com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp

import com.panabuntu.weathertracker.core.presentation.util.UiText

data class DayForecastEntityView(
    val timestamp: Long,
    val iconUrl: String?,
    val dayName: UiText,
    val dayOfMonth: Int,
    val monthName: UiText,
    val maxTemp: String,
    val minTemp: String,
    val description: String?
)
