package com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TempDto(
    @SerialName("day")
    val day: Float,
    @SerialName("eve")
    val eve: Float,
    @SerialName("max")
    val max: Float,
    @SerialName("min")
    val min: Float,
    @SerialName("morn")
    val morn: Float,
    @SerialName("night")
    val night: Float
)