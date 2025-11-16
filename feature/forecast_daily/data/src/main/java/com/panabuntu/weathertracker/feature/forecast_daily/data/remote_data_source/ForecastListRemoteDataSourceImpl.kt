package com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source

import com.panabuntu.weathertracker.core.data.data_source.RemoteDataSource
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_list.data.remote_data_source.dto.ForecastDaily
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ForecastListRemoteDataSourceImpl(
    private val httpClient: HttpClient,
): RemoteDataSource() {

    suspend fun getDailyForecast(
        lat: Double,
        lon: Double,
    ): Result<ForecastDaily, NetworkError> {
        return getResult<ForecastDaily> {
            httpClient.get {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("units", "metric")
                parameter("exclude", "minutely,hourly,current,alerts")
            }
        }
    }

}