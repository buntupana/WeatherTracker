package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

sealed class ForecastDayDetailIntent {
    data object GetDayDetail: ForecastDayDetailIntent()
    data object NavigateBack: ForecastDayDetailIntent()
}