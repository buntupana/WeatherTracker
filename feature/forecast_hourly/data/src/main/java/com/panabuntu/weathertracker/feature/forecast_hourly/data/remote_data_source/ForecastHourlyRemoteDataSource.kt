package com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source

import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto.ForecastDayDetailDto

interface ForecastHourlyRemoteDataSource {
    suspend fun getDayDetailForecast(lat: Double, lon: Double): Result<ForecastDayDetailDto, NetworkError>
}