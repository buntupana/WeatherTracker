package com.panabuntu.weathertracker.feature.forecast_daily.data.mapper

import com.panabuntu.weathertracker.core.data.entity.DailyEntity
import com.panabuntu.weathertracker.core.domain.util.toLocalDate
import com.panabuntu.weathertracker.feature.forecast_daily.repository.model.Daily
import java.time.format.DateTimeParseException

@Throws(DateTimeParseException::class)
fun List<DailyEntity>.toModel(): List<Daily> {

    return map {
        Daily(
            date = it.date.toLocalDate(),
            maxTemp = it.maxTemp,
            minTemp = it.minTemp,
            description = it.description,
            icon = it.icon
        )
    }
}