package com.panabuntu.weathertracker.feature.forecast_day_list.presentation.forecast_day_list

import com.panabuntu.weathertracker.feature.core.presentation.comp.DayForecastItem

data class ForecastDailyState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val locationName: String,
    val lat: Double,
    val lon: Double,
    val dailyList: List<DayForecastItem> = emptyList()
)