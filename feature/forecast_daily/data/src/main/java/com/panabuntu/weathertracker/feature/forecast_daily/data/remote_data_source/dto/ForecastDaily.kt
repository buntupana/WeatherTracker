package com.panabuntu.weathertracker.feature.forecast_list.data.remote_data_source.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDaily(
    @SerialName("daily")
    val daily: List<Daily>,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_offset")
    val timezoneOffset: Int
)