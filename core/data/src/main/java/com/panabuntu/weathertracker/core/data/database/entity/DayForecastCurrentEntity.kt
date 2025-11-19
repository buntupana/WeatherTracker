package com.panabuntu.weathertracker.core.data.database.entity

data class DayForecastCurrentEntity(
    val date: Long,
    val lat: Double,
    val lon: Double,
    val currentTemperature: Float,
    val currentFeelsLike: Float,
    val currentIcon: String?,
    val currentDescription: String?,
    val currentHumidity: Int,
    val currentWindSpeed: Float,
    val currentWindDirectionDegrees: Int,
    val currentUvIndex: Float,
)
