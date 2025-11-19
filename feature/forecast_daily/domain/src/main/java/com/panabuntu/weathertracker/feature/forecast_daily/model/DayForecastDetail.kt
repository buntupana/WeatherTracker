package com.panabuntu.weathertracker.feature.forecast_daily.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DayForecastDetail(
    val date: LocalDate,
    val timestamp: Long,
    val maxTemp: Float,
    val minTemp: Float,
    val iconUrl: String?,
    val description: String?,
    val humidity: Int,
    val windSpeed: Float,
    val windDirectionDegrees: Int,
    val uvIndex: Float?,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val rainProbability: Float
)
