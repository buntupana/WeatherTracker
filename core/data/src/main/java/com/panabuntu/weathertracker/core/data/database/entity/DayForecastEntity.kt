package com.panabuntu.weathertracker.core.data.database.entity


import androidx.room.Embedded
import androidx.room.Entity

@Entity(
    tableName = "day_forecast",
    primaryKeys = ["date", "lat", "lon"]
)
data class DayForecastEntity(
    val date: Long,
    val lat: Double,
    val lon: Double,
    val maxTemp: Float,
    val minTemp: Float,
    val description: String?,
    val icon: String?,
    val sunrise: Long,
    val sunset: Long,
    @Embedded
    val current: Current?
)