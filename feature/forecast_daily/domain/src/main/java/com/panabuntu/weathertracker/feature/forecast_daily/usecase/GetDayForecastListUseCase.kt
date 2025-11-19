package com.panabuntu.weathertracker.feature.core.usecase

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.core.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.core.repository.ForecastDailyRepository
import kotlinx.coroutines.flow.Flow

class GetDayForecastListUseCase(
    private val repository: ForecastDailyRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Flow<Result<List<DayForecastSimple>, Error>> {
        return repository.getDayListForecast(lat = lat, lon = lon)
    }
}