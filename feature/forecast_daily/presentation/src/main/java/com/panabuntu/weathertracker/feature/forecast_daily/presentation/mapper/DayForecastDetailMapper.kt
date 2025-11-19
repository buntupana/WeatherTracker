package com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper

import androidx.compose.ui.util.fastRoundToInt
import com.panabuntu.weathertracker.core.domain.util.Units
import com.panabuntu.weathertracker.core.domain.util.toFriendlyTime
import com.panabuntu.weathertracker.core.presentation.R
import com.panabuntu.weathertracker.core.presentation.util.DateLangUtil
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.ForecastDetailInfo

fun DayForecastDetail?.toForecastDetailInfo(
    units: Units = Units.METRIC
): ForecastDetailInfo? {

    if (this == null) return null

    val windSpeed = when (units) {
        Units.IMPERIAL -> UiText.StringResource(
            R.string.core_imperial_speed_unit,
            windSpeed.fastRoundToInt()
        )

        Units.STANDARD -> UiText.StringResource(
            R.string.core_imperial_speed_unit,
            (windSpeed * 100).fastRoundToInt()
        )

        Units.METRIC -> UiText.StringResource(
            R.string.core_imperial_speed_unit,
            (windSpeed * 100).fastRoundToInt()
        )
    }

    return ForecastDetailInfo(
        dayName = UiText.StringResource(DateLangUtil.getResIdNameOfDay(date)),
        iconUrl = iconUrl,
        minTemp = "$minTemp°",
        maxTemp = "$maxTemp°",
        monthName = UiText.StringResource(
            DateLangUtil.getResIdNameOfMonth(date)
        ),
        windSpeed = windSpeed,
        sunrise = sunrise.toFriendlyTime(),
        sunset = sunset.toFriendlyTime(),
        humidity = "$humidity%",
        uvIndex = "$uvIndex",
        rainProbability = "${(rainProbability * 100).fastRoundToInt()}%"
    )
}