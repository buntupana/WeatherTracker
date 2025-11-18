package com.panabuntu.weathertracker.feature.forecast_daily.repository.usecase

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.repository.model.Daily
import com.panabuntu.weathertracker.feature.forecast_daily.repository.repository.ForecastDailyRepository
import kotlinx.coroutines.flow.Flow

class GetDailyForecastUseCase(
    private val repository: ForecastDailyRepository
) {

    suspend operator fun invoke(): Flow<Result<List<Daily>, Error>> {
        return repository.getDaily(lat = 40.4983, lon = -3.5676)
    }
}