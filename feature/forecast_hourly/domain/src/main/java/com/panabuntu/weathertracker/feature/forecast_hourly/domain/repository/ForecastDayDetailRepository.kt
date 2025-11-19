package com.panabuntu.weathertracker.feature.forecast_hourly.domain.repository

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_hourly.domain.model.DayForecastDetail
import kotlinx.coroutines.flow.Flow

interface ForecastDayDetailRepository {
    suspend fun getDayDetail(
        lat: Double,
        lon: Double
    ): Flow<Result<DayForecastDetail?, Error>>
}