package com.panabuntu.weathertracker.feature.forecast_hourly.data.mapper

import com.panabuntu.weathertracker.core.data.database.entity.HourForecastEntity
import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto.HourlyDto

fun List<HourlyDto>.toEntity(
    dateStartOfDay: Long,
    lat: Double,
    lon: Double
): List<HourForecastEntity> {

    return map {
        HourForecastEntity(
            date = it.dt,
            lat = lat,
            lon = lon,
            dateStartOfDay = dateStartOfDay,
            precipitationProbability = it.pop,
            temperature = it.temp,
            icon = it.weather.firstOrNull()?.icon
        )
    }
}