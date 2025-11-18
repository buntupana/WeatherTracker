package com.panabuntu.weathertracker.navigation

import androidx.compose.runtime.Composable
import com.panabuntu.weathertracker.core.presentation.navigation.NavRoutesMain
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.ForecastDailyScreen

@Composable
fun ForeCastDailyRouteNav(
    navRoutesMain: NavRoutesMain
) {
    ForecastDailyScreen()
}