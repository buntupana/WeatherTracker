package com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto


import com.panabuntu.weathertracker.core.data.remote_data_source.dto.WeatherDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayForecastDto(
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("dew_point")
    val dewPoint: Float,
    @SerialName("dt")
    val dt: Long,
    @SerialName("feels_like")
    val feelsLike: FeelsLikeDto,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("moon_phase")
    val moonPhase: Float,
    @SerialName("moonrise")
    val moonrise: Int,
    @SerialName("moonset")
    val moonset: Int,
    @SerialName("pop")
    val pop: Float,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("rain")
    val rain: Float? = null,
    @SerialName("summary")
    val summary: String,
    @SerialName("sunrise")
    val sunrise: Long,
    @SerialName("sunset")
    val sunset: Long,
    @SerialName("temp")
    val temp: TempDto,
    @SerialName("uvi")
    val uvi: Float,
    @SerialName("weather")
    val weather: List<WeatherDto>,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Float? = null,
    @SerialName("wind_speed")
    val windSpeed: Float,
    @SerialName("snow")
    val snow: Float? = null
)