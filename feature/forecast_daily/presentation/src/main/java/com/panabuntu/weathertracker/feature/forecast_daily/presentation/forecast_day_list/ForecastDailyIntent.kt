package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list

sealed class ForecastDailyIntent {
    data object GetDailyForecast: ForecastDailyIntent()
    data class OnDayClick(val date: Long): ForecastDailyIntent()
}