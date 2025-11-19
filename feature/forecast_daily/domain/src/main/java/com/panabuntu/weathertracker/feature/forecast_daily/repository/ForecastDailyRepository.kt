package com.panabuntu.weathertracker.feature.core.repository

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.core.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.core.model.DayForecastSimple
import kotlinx.coroutines.flow.Flow

interface ForecastDailyRepository {
    suspend fun getDayListForecast(lat: Double, lon: Double): Flow<Result<List<DayForecastSimple>, Error>>
    suspend fun getDayForecast(
        date: Long,
        lat: Double,
        lon: Double
    ): Flow<Result<DayForecastDetail?, Error>>
}