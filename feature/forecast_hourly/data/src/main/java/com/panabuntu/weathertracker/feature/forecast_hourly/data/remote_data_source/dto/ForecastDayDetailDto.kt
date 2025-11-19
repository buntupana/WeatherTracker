package com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDetailDto(
    @SerialName("current")
    val current: CurrentDto,
    @SerialName("hourly")
    val hourly: List<HourlyDto>,
    @SerialName("alerts")
    val alerts: List<AlertDto>,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_offset")
    val timezoneOffset: Int
)