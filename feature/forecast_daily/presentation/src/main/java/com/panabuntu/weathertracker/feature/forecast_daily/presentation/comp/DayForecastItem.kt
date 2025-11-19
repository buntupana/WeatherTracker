package com.panabuntu.weathertracker.feature.core.presentation.comp

import com.panabuntu.weathertracker.core.presentation.util.UiText

data class DayForecastItem(
    val timestamp: Long,
    val iconUrl: String?,
    val dayName: UiText,
    val dayOfMonth: Int,
    val monthName: UiText,
    val maxTemp: String,
    val minTemp: String,
    val description: String?
)
