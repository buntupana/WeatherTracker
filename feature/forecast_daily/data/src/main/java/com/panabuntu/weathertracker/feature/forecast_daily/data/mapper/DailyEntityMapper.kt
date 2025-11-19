package com.panabuntu.weathertracker.feature.core.data.mapper

import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity
import com.panabuntu.weathertracker.core.domain.util.toLocalDate
import com.panabuntu.weathertracker.core.domain.util.toLocalDateTime
import com.panabuntu.weathertracker.feature.core.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.core.model.DayForecastSimple
import java.time.format.DateTimeParseException

@Throws(DateTimeParseException::class)
fun List<DayForecastEntity>.toDayForecastSimple(
    createIconUrl: (icon: String) -> String
): List<DayForecastSimple> {

    return map {
        it.toDayForecastSimple(createIconUrl)
    }
}

@Throws(DateTimeParseException::class)
fun DayForecastEntity.toDayForecastSimple(
    createIconUrl: (icon: String) -> String
): DayForecastSimple {
    val iconUrl = if (icon == null) null else createIconUrl(icon!!)

    return DayForecastSimple(
        date = date.toLocalDate(),
        timestamp = date,
        maxTemp = maxTemp,
        minTemp = minTemp,
        description = description,
        iconUrl = iconUrl
    )
}

@Throws(DateTimeParseException::class)
fun DayForecastEntity.toDayForecastDetail(
    createIconUrl: (icon: String) -> String
): DayForecastDetail {
    val iconUrl = if (icon == null) null else createIconUrl(icon!!)

    return DayForecastDetail(
        date = date.toLocalDate(),
        timestamp = date,
        maxTemp = maxTemp,
        minTemp = minTemp,
        description = description,
        iconUrl = iconUrl,
        sunrise = sunrise.toLocalDateTime(),
        sunset = sunset.toLocalDateTime(),
        humidity = humidity,
        windSpeed = windSpeed,
        windDirectionDegrees = windDirectionDegrees,
        uvIndex = uvIndex,
        rainProbability = pop
    )
}