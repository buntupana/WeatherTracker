package com.panabuntu.weathertracker.feature.forecast_daily.usecase

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.model.Daily
import com.panabuntu.weathertracker.feature.forecast_daily.repository.ForecastDailyRepository
import kotlinx.coroutines.flow.Flow

class GetDailyForecastUseCase(
    private val repository: ForecastDailyRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Flow<Result<List<Daily>, Error>> {
        return repository.getDaily(lat = lat, lon = lon)
    }
}