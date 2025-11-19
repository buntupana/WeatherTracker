package com.panabuntu.weathertracker.core.data.database.entity

data class Current(
    val currentTemperature: Float,
    val currentFeelsLike: Float,
    val currentIcon: String?,
    val currentDescription: String?,
    val currentHumidity: Int,
    val currentWindSpeed: Float,
    val currentWindDirectionDegrees: Int,
    val currentUvIndex: Float,
)
