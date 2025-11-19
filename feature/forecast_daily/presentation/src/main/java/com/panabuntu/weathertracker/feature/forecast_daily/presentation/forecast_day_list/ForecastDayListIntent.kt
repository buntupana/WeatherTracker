package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list

sealed class ForecastDayListIntent {
    data object GetDailyForecast: ForecastDayListIntent()
    data class OnDayClick(val date: Long): ForecastDayListIntent()
}