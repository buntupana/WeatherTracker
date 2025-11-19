package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import com.panabuntu.weathertracker.core.presentation.util.UiText

data class ForecastDetailInfo(
    val dayName: UiText,
    val monthName: UiText,
    val iconUrl: String? = null,
    val minTemp: String,
    val maxTemp: String,
    val windSpeed: String,
    val sunrise: String,
    val sunset: String,
    val humidity: String,
    val uvIndex: String,
    val rainProbability: String
)
