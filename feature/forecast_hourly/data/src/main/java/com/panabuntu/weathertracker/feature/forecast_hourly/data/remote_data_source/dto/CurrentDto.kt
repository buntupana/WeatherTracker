package com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    @SerialName("dt")
    val dt: Long,
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("dew_point")
    val dewPoint: Float,
    @SerialName("feels_like")
    val feelsLike: Float,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int,
    @SerialName("temp")
    val temp: Float,
    @SerialName("uvi")
    val uvi: Float,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("weather")
    val weather: List<Weather>,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_speed")
    val windSpeed: Float,
    @SerialName("wind_gust")
    val windGust: Float? = null,
    @SerialName("rain")
    val rain: RainDto,
    @SerialName("snow")
    val snow: RainDto,
)