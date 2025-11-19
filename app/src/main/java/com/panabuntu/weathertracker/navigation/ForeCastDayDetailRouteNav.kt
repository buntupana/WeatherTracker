package com.panabuntu.weathertracker.navigation

import androidx.compose.runtime.Composable
import com.panabuntu.weathertracker.core.presentation.navigation.NavRoutesMain
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.ForecastDayDetailScreen

@Composable
fun ForeCastDayDetailRouteNav(
    navRoutesMain: NavRoutesMain
) {
    ForecastDayDetailScreen(
        navigateBackClick = { navRoutesMain.popBackStack() }
    )
}