package com.panabuntu.weathertracker.feature.forecast_list.data.remote_data_source.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: String
)