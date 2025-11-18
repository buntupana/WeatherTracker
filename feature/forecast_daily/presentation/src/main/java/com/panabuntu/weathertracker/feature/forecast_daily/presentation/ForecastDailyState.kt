package com.panabuntu.weathertracker.feature.forecast_daily.presentation

import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.DayForecastEntityView

data class ForecastDailyState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val locationName: String,
    val dailyList: List<DayForecastEntityView> = emptyList()
)