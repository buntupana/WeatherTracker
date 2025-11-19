package com.panabuntu.weathertracker.feature.forecast_hourly.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DayForecastDetail(
    val date: LocalDate,
    val temperature: Float?,
    val feelsLike: Float?,
    val iconUrl: String?,
    val description: String?,
    val humidity: Int?,
    val windSpeed: Float?,
    val windDirectionDegrees: Int?,
    val uvIndex: Float?,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val hourForecastList: List<HourForecast>
)
