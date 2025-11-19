package com.panabuntu.weathertracker.core.data.database.entity

import androidx.room.Entity


@Entity(
    tableName = "hour_forecast",
    primaryKeys = ["date", "lat", "lon"]
)
data class HourForecastEntity(
    val date: Long,
    val dateStartOfDay: Long,
    val lat: Double,
    val lon: Double,
    val temperature: Float,
    val precipitationProbability: Float,
    val icon: String?
)
