package com.panabuntu.weathertracker.feature.forecast_daily.data.repository

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.data.util.DateUtils
import com.panabuntu.weathertracker.core.data.util.networkBoundResource
import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toModel
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.ForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.repository.model.Daily
import com.panabuntu.weathertracker.feature.forecast_daily.repository.repository.ForecastDailyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ForecastDailyRepositoryImpl(
    private val remoteDataSource: ForecastDailyRemoteDataSource,
    private val database: AppDataBase
) : ForecastDailyRepository {

    override suspend fun getDaily(
        lat: Double,
        lon: Double
    ): Flow<Result<List<Daily>, Error>> {

        return networkBoundResource(
            query = {
                database.dailyDao.deleteOlderThan(DateUtils.getCurrentDayTimestampUTC())
                database.dailyDao.getAll().map { it.toModel() }
            },
            fetch = {
                remoteDataSource.getDailyForecast(lat = lat, lon = lon)
            },
            saveFetchResult = {
                database.dailyDao.upsertAll(it.daily.toEntity())
            }
        )
    }
}