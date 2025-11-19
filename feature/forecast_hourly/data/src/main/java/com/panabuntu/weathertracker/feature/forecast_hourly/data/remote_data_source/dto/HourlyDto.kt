package com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto


import com.panabuntu.weathertracker.core.data.remote_data_source.dto.WeatherDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDto(
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("dew_point")
    val dewPoint: Float,
    @SerialName("dt")
    val dt: Long,
    @SerialName("feels_like")
    val feelsLike: Float,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("pop")
    val pop: Float,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("rain")
    val rain: RainDto,
    @SerialName("snow")
    val snow: RainDto,
    @SerialName("temp")
    val temp: Float,
    @SerialName("uvi")
    val uvi: Float,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("weather")
    val weather: List<WeatherDto>,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Float? = null,
    @SerialName("wind_speed")
    val windSpeed: Float
)