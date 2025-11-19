package com.panabuntu.weathertracker.feature.forecast_hourly.data.mapper

import com.panabuntu.weathertracker.core.data.database.entity.DayWithHours
import com.panabuntu.weathertracker.core.domain.util.toLocalDate
import com.panabuntu.weathertracker.core.domain.util.toLocalDateTime
import com.panabuntu.weathertracker.feature.forecast_hourly.domain.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.forecast_hourly.domain.model.HourForecast

fun DayWithHours.toModel(
    createIconUrl: (icon: String) -> String
): DayForecastDetail {

    val hourForecastList = hours.map {

        val iconUrl = if (it.icon == null) null else createIconUrl(it.icon!!)

        HourForecast(
            date = it.date.toLocalDate(),
            temperature = it.temperature,
            precipitationProbability = it.precipitationProbability,
            iconUrl = iconUrl
        )
    }

    val iconUrl = if (day.current?.currentIcon == null) null else createIconUrl(day.current?.currentIcon!!)

    return DayForecastDetail(
        date = day.date.toLocalDate(),
        iconUrl = iconUrl,
        temperature = day.current?.currentTemperature,
        feelsLike = day.current?.currentFeelsLike,
        description = day.current?.currentDescription,
        sunrise = day.sunrise.toLocalDateTime(),
        sunset = day.sunset.toLocalDateTime(),
        humidity = day.current?.currentHumidity,
        windSpeed = day.current?.currentWindSpeed,
        uvIndex = day.current?.currentUvIndex,
        hourForecastList = hourForecastList,
        windDirectionDegrees = day.current?.currentWindDirectionDegrees
    )
}