package com.panabuntu.weathertracker.feature.forecast_daily.presentation

import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.DayForecastEntityView

data class ForecastDailyState(
    val isLoading: Boolean = true,
    val dailyList: List<DayForecastEntityView>? = null,
    val isLoadingError: Boolean = false,
    val errorMessage: UiText? = null
)