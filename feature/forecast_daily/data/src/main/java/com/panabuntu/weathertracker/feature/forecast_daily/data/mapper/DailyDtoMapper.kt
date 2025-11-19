package com.panabuntu.weathertracker.feature.forecast_daily.data.mapper

import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto.DayForecastDto

fun List<DayForecastDto>.toDayForecastEntity(
    lat: Double,
    lon: Double
): List<DayForecastEntity> {
    return map {
        DayForecastEntity(
            date = it.dt,
            lat = lat,
            lon = lon,
            maxTemp = it.temp.max,
            minTemp = it.temp.min,
            description = it.weather.firstOrNull()?.description,
            icon = it.weather.firstOrNull()?.icon,
            sunrise = it.sunrise,
            sunset = it.sunset,
            pop = it.pop,
            humidity = it.humidity,
            windSpeed = it.windSpeed,
            windDirectionDegrees = it.windDeg,
            uvIndex = it.uvi
        )
    }
}