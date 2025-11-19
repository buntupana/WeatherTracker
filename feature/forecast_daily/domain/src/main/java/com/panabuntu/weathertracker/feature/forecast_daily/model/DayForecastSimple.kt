package com.panabuntu.weathertracker.feature.forecast_daily.model

import java.time.LocalDate

data class DayForecastSimple(
    val date: LocalDate,
    val timestamp: Long,
    val maxTemp: Float,
    val minTemp: Float,
    val description: String?,
    val iconUrl: String?
)
