package com.panabuntu.weathertracker.feature.forecast_hourly.data.mapper

import com.panabuntu.weathertracker.core.data.database.entity.DayForecastCurrentEntity
import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto.CurrentDto

fun CurrentDto.toEntity(
    date: Long,
    lat: Double,
    lon: Double
): DayForecastCurrentEntity {
    return DayForecastCurrentEntity(
        date = date,
        lat = lat,
        lon = lon,
        currentTemperature = temp,
        currentFeelsLike = feelsLike,
        currentIcon = weather.firstOrNull()?.icon,
        currentDescription = weather.firstOrNull()?.description,
        currentHumidity = humidity,
        currentWindSpeed = windSpeed,
        currentWindDirectionDegrees = windDeg,
        currentUvIndex = uvi,
    )
}