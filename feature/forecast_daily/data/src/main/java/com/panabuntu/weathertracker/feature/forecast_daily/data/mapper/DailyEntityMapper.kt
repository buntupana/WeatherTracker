package com.panabuntu.weathertracker.feature.forecast_daily.data.mapper

import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity
import com.panabuntu.weathertracker.core.domain.util.toLocalDate
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastSimple
import java.time.format.DateTimeParseException

@Throws(DateTimeParseException::class)
fun List<DayForecastEntity>.toModel(
    createIconUrl: (icon: String) -> String
): List<DayForecastSimple> {

    return map {

        val iconUrl = if(it.icon == null) null else createIconUrl(it.icon!!)

        DayForecastSimple(
            date = it.date.toLocalDate(),
            maxTemp = it.maxTemp,
            minTemp = it.minTemp,
            description = it.description,
            iconUrl = iconUrl
        )
    }
}