package com.panabuntu.weathertracker.navigation

import androidx.compose.runtime.Composable
import com.panabuntu.weathertracker.core.presentation.navigation.NavRoutesMain
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.ForecastDayDetailRoute
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list.ForecastDailyScreen

@Composable
fun ForeCastDailyRouteNav(
    navRoutesMain: NavRoutesMain
) {
    ForecastDailyScreen(
        onDayClick = { date, lat, lon, locationName ->
            navRoutesMain.navigate(
                ForecastDayDetailRoute(
                    date = date,
                    lat = lat,
                    lon = lon,
                    locationName = locationName
                )
            )
        }
    )
}