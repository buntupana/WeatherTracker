package com.panabuntu.weathertracker.feature.core.usecase

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.core.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.core.repository.ForecastDailyRepository
import kotlinx.coroutines.flow.Flow

class GetDayForecastDetailUseCase(
    private val repository: ForecastDailyRepository
) {
    suspend operator fun invoke(date: Long, lat: Double, lon: Double): Flow<Result<DayForecastDetail?, Error>> {
        return repository.getDayForecast(date =date, lat = lat, lon = lon)
    }
}