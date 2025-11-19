package com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper

import com.panabuntu.weathertracker.core.presentation.util.DateLangUtil
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.core.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.ForecastDetailInfo

fun DayForecastDetail?.toForecastDetailInfo(
    units: Unit
): ForecastDetailInfo? {

    if (this == null) return null

    return ForecastDetailInfo(
        dayName = UiText.StringResource(DateLangUtil.getResIdNameOfDay(date)),
        iconUrl = iconUrl,
        minTemp = "$minTemp°",
        maxTemp = "$maxTemp°",
        monthName = UiText.StringResource(
            DateLangUtil.getResIdNameOfMonth(date)
        ),
        windSpeed = "$windSpeed m/s",
        sunrise = sunrise.toLocalTime().toString(),
        sunset = sunset.toLocalTime().toString(),
        humidity = "$humidity%",
        uvIndex = "$uvIndex",
        rainProbability = "$rainProbability%"
    )
}