package com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper

import androidx.compose.ui.util.fastRoundToInt
import com.panabuntu.weathertracker.core.presentation.util.DateLangUtil
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.core.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.core.presentation.comp.DayForecastItem

fun DayForecastSimple.toDayForecastItem(): DayForecastItem {
    val dayResId = DateLangUtil.getResIdNameOfDay(date)

    val monthResId = DateLangUtil.getResIdNameOfMonth(date)

    val maxTempStr = "${maxTemp.fastRoundToInt()}°"
    val minTempStr = "${minTemp.fastRoundToInt()}°"

    return DayForecastItem(
        timestamp = timestamp,
        iconUrl = iconUrl,
        dayName = UiText.StringResource(dayResId),
        dayOfMonth = date.dayOfMonth,
        monthName = UiText.StringResource(monthResId),
        maxTemp = maxTempStr,
        minTemp = minTempStr,
        description = description,
    )
}

fun List<DayForecastSimple>.toDayForecastItem(): List<DayForecastItem> {
    return map { it.toDayForecastItem() }
}