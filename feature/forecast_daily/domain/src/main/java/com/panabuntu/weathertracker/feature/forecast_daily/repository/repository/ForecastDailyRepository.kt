package com.panabuntu.weathertracker.feature.forecast_daily.repository.repository

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.repository.model.Daily
import kotlinx.coroutines.flow.Flow

interface ForecastDailyRepository {
    suspend fun getDaily(lat: Double, lon: Double): Flow<Result<List<Daily>, Error>>
}