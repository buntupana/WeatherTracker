package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import com.panabuntu.weathertracker.core.presentation.util.UiText

data class ForecastDayDetailState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: UiText? = null
)