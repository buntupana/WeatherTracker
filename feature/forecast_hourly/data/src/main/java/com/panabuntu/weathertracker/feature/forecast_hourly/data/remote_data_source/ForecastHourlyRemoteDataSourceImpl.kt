package com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source

import com.panabuntu.weathertracker.core.data.remote_data_source.RemoteDataSource
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto.ForecastDayDetailDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ForecastHourlyRemoteDataSourceImpl(
    private val httpClient: HttpClient
): RemoteDataSource(),  ForecastHourlyRemoteDataSource {

    override suspend fun getDayDetailForecast(
        lat: Double,
        lon: Double,
    ): Result<ForecastDayDetailDto, NetworkError> {
        return getResult<ForecastDayDetailDto> {
            httpClient.get {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("exclude", "minutely,daily,alerts")
            }
        }
    }
}