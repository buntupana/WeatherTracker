package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import com.panabuntu.weathertracker.core.presentation.util.UiText

data class ForecastDetailInfo(
    val dayName: UiText,
    val description: String?,
    val monthName: UiText,
    val dayOfMonth: Int,
    val iconUrl: String? = null,
    val minTemp: String,
    val maxTemp: String,
    val windSpeed: UiText,
    val sunrise: String,
    val sunset: String,
    val humidity: String,
    val uvIndex: String,
    val rainProbability: String
)
