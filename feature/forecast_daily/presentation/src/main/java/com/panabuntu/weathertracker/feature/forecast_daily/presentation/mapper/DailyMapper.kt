package com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper

import androidx.compose.ui.util.fastRoundToInt
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.DayForecastEntityView
import com.panabuntu.weathertracker.feature.forecast_daily.repository.model.Daily
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

    val dateFormat = "${date.dayOfMonth}/${date.monthValue}"

    val maxTempStr = "${maxTemp.fastRoundToInt()}°"
    val minTempStr = "${minTemp.fastRoundToInt()}°"

    return DayForecastEntityView(
        timestamp = date.toEpochDay(),
        iconUrl = iconUrl,
        day = UiText.StringResource(dayResId),
        date = dateFormat,
        maxTemp = maxTempStr,
        minTemp = minTempStr,
        description = description,
    )
}

fun List<Daily>.toViewEntity(): List<DayForecastEntityView> {
    return map { it.toViewEntity() }
}