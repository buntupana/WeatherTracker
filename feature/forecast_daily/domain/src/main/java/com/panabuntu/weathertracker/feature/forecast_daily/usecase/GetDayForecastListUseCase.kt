package com.panabuntu.weathertracker.feature.forecast_daily.usecase

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.repository.ForecastDailyRepository
import kotlinx.coroutines.flow.Flow

class GetDayForecastListUseCase(
    private val repository: ForecastDailyRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Flow<Result<List<DayForecastSimple>, Error>> {
        return repository.getDayForecastList(lat = lat, lon = lon)
    }
}