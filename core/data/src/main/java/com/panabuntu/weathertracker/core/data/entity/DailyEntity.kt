package com.panabuntu.weathertracker.core.data.entity


import androidx.room.Entity

@Entity(
    tableName = "daily",
    primaryKeys = ["date", "lat", "lon"]
)
data class DailyEntity(
    val date: Long,
    val lat: Double,
    val lon: Double,
    val maxTemp: Float,
    val minTemp: Float,
    val description: String?,
    val icon: String?
)