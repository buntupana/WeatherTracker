package com.panabuntu.weathertracker.feature.forecast_daily.model

import java.time.LocalDate

data class Daily(
    val date: LocalDate,
    val maxTemp: Float,
    val minTemp: Float,
    val description: String?,
    val iconUrl: String?
)
