package com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeelsLikeDto(
    @SerialName("day")
    val day: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("morn")
    val morn: Double,
    @SerialName("night")
    val night: Double
)