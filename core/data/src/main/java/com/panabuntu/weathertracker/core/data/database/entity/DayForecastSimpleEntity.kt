package com.panabuntu.weathertracker.core.data.database.entity


data class DayForecastSimpleEntity(
    val date: Long,
    val lat: Double,
    val lon: Double,
    val maxTemp: Float,
    val minTemp: Float,
    val description: String?,
    val icon: String?,
    val sunrise: Long,
    val sunset: Long,
)