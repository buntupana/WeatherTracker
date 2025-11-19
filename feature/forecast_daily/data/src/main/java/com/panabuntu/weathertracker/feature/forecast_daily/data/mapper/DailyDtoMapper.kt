package com.panabuntu.weathertracker.feature.forecast_daily.data.mapper

import com.panabuntu.weathertracker.core.data.database.entity.DayForecastSimpleEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto.DailyDto

fun List<DailyDto>.toEntity(
    lat: Double,
    lon: Double
): List<DayForecastSimpleEntity> {
    return map {
        DayForecastSimpleEntity(
            date = it.dt,
            lat = lat,
            lon = lon,
            maxTemp = it.temp.max,
            minTemp = it.temp.min,
            description = it.weather.firstOrNull()?.description,
            icon = it.weather.firstOrNull()?.icon,
            sunrise = it.sunrise,
            sunset = it.sunset,
        )
    }
}