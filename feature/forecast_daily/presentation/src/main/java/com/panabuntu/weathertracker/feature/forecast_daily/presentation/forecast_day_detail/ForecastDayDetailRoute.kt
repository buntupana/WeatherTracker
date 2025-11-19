package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import com.panabuntu.weathertracker.core.presentation.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDetailRoute(
    val date: Long,
    val locationName: String,
    val lat: Double,
    val lon: Double
) : Route