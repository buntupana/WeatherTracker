package com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper

import androidx.compose.ui.util.fastRoundToInt
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.DayForecastEntityView
import com.panabuntu.weathertracker.feature.forecast_daily.model.Daily
import com.panabuntu.weathertracker.forecast_list.presentation.R
import java.time.LocalDate

fun Daily.toViewEntity(): DayForecastEntityView {
    val day = date.dayOfWeek.value

    val currentDate = LocalDate.now()

    val dayResId = when {
        currentDate.dayOfWeek.value == day && currentDate.dayOfYear == date.dayOfYear -> R.string.forecast_daily_today
        day == 1 -> R.string.forecast_daily_monday
        day == 2 -> R.string.forecast_daily_tuesday
        day == 3 -> R.string.forecast_daily_wednesday
        day == 4 -> R.string.forecast_daily_thursday
        day == 5 -> R.string.forecast_daily_friday
        day == 6 -> R.string.forecast_daily_saturday
        day == 7 -> R.string.forecast_daily_sunday
        else -> R.string.forecast_daily_sunday
    }

    val monthResId = when (date.monthValue) {
        1 -> R.string.forecast_daily_january_short
        2 -> R.string.forecast_daily_february_short
        3 -> R.string.forecast_daily_march_short
        4 -> R.string.forecast_daily_april_short
        5 -> R.string.forecast_daily_may_short
        6 -> R.string.forecast_daily_june_short
        7 -> R.string.forecast_daily_july_short
        8 -> R.string.forecast_daily_august_short
        9 -> R.string.forecast_daily_september_short
        10 -> R.string.forecast_daily_october_short
        11 -> R.string.forecast_daily_november_short
        12 -> R.string.forecast_daily_december_short
        else -> R.string.forecast_daily_january_short
    }
    val maxTempStr = "${maxTemp.fastRoundToInt()}°"
    val minTempStr = "${minTemp.fastRoundToInt()}°"

    return DayForecastEntityView(
        timestamp = date.toEpochDay(),
        iconUrl = iconUrl,
        dayName = UiText.StringResource(dayResId),
        dayOfMonth = date.dayOfMonth,
        monthName = UiText.StringResource(monthResId),
        maxTemp = maxTempStr,
        minTemp = minTempStr,
        description = description,
    )
}

fun List<Daily>.toViewEntity(): List<DayForecastEntityView> {
    return map { it.toViewEntity() }
}