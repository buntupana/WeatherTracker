package com.panabuntu.weathertracker.feature.core.data.remote_data_source

import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.core.data.remote_data_source.dto.ForecastDailyDto

interface ForecastDailyRemoteDataSource {

    suspend fun getDailyForecast(
        lat: Double,
        lon: Double,
    ): Result<ForecastDailyDto, NetworkError>
}