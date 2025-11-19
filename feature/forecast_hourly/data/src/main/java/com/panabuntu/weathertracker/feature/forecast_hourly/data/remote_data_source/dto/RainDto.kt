package com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RainDto(
    @SerialName("1h")
    val oneHour: Double? = null
)