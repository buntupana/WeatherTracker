package com.panabuntu.weathertracker.feature.forecast_daily.data.dummy_data

import com.panabuntu.weathertracker.core.data.remote_data_source.dto.WeatherDto
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto.DayForecastDto
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto.FeelsLikeDto
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto.ForecastDailyDto
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.dto.TempDto
import java.time.LocalDate
import java.time.ZoneOffset

object ForecastDailyDtoDummy {

    fun getSuccess(
        numberOfItems: Int = 8,
        startFromLocalDate: LocalDate = LocalDate.now()
    ): Result.Success<ForecastDailyDto> {
        return Result.Success(
            ForecastDailyDto(
                lat = 40.4983,
                lon = -3.5676,
                timezone = "Europe/Madrid",
                timezoneOffset = 3600,
                daily = getDailyList(
                    numberOfItems = numberOfItems,
                    startFromLocalDate = startFromLocalDate
                )
            )
        )
    }

    private fun getDailyList(
        numberOfItems: Int = 8,
        startFromLocalDate: LocalDate = LocalDate.now()
    ): List<DayForecastDto> {

        return (0..numberOfItems).map { dayIndex ->
            val date = startFromLocalDate
                .plusDays(dayIndex.toLong())
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC)
                .epochSecond

            DayForecastDto(
                dt = date,
                sunrise = 1763276514,
                sunset = 1763312188,
                moonrise = 1763263260,
                moonset = 17633047,
                moonPhase = 0.88f,
                temp = TempDto(
                    day = 11.5f,
                    min = 7.58f,
                    max = 13.65f,
                    night = 7.58f,
                    eve = 13.09f,
                    morn = 9.36f
                ),
                feelsLike = FeelsLikeDto(
                    day = 10.79f,
                    night = 5.77f,
                    eve = 12.31f,
                    morn = 7.04f,
                ),
                pressure = 1011,
                humidity = 80,
                dewPoint = 7.42f,
                windSpeed = 6.93f,
                windDeg = 241,
                windGust = 11.32f,
                weather = listOf(
                    WeatherDto(
                        id = 803,
                        main = "Clouds",
                        description = "broken clouds",
                        icon = "04"
                    )
                ),
                clouds = 100,
                pop = 0.8f,
                uvi = 1.03f,
                rain = 0.0f,
                summary = "Expect a day of partly cloudy with clear spells"
            )
        }
    }
}