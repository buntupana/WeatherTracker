package com.panabuntu.weathertracker.feature.forecast_hourly.domain.model

import java.time.LocalDate

data class HourForecast(
    val date: LocalDate,
    val temperature: Float,
    val precipitationProbability: Float,
    val iconUrl: String?
)
