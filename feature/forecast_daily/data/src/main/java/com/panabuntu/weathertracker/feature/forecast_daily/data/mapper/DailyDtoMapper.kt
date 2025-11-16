package com.panabuntu.weathertracker.feature.forecast_daily.data.mapper

import com.panabuntu.weathertracker.core.data.entity.DailyEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto.DailyDto

fun List<DailyDto>.toEntity() : List<DailyEntity> {
    return map {
        DailyEntity(
            date = it.dt,
            maxTemp = it.temp.max,
            minTemp = it.temp.min,
            description = it.weather.firstOrNull()?.description,
            icon = it.weather.firstOrNull()?.icon
        )
    }
}