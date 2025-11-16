package com.panabuntu.weathertracker.feature.forecast_list.data.remote_data_source.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Temp(
    @SerialName("day")
    val day: Double,
    @SerialName("eve")
    val eve: Double,
    @SerialName("max")
    val max: Double,
    @SerialName("min")
    val min: Double,
    @SerialName("morn")
    val morn: Double,
    @SerialName("night")
    val night: Double
)