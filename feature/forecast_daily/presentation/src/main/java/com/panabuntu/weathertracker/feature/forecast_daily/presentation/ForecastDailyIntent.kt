package com.panabuntu.weathertracker.feature.forecast_daily.presentation

sealed class ForecastDailyIntent {
    data object GetDailyForecast: ForecastDailyIntent()
}