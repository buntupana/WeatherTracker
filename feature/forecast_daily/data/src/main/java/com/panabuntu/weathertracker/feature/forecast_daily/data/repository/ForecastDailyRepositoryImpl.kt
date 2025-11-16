package com.panabuntu.weathertracker.feature.forecast_daily.data.repository

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.data.util.getFlowResult
import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toModel
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.ForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.repository.model.Daily
import com.panabuntu.weathertracker.feature.forecast_daily.repository.repository.ForecastListRepository
import kotlinx.coroutines.flow.Flow
import java.time.Instant

class ForecastDailyRepositoryImpl(
    private val remoteDataSource: ForecastDailyRemoteDataSource,
    private val database: AppDataBase
) : ForecastListRepository {

    override suspend fun getDaily(
        lat: Double,
        lon: Double
    ): Flow<Result<List<Daily>, Error>> {
        return getFlowResult(
            prevDataBaseQuery = {
                database.dailyDao.deleteOlderThan(Instant.now().toEpochMilli())
            },
            networkCall = {
                remoteDataSource.getDailyForecast(lat = lat, lon = lon)
            },
            mapToEntity = {
                it.daily.toEntity()
            },
            updateDataBaseQuery = {
                database.dailyDao.upsertAll(it)
            },
            fetchFromDataBaseQuery = {
                database.dailyDao.getAll()
            },
            mapToModel = {
                it.toModel()
            }
        )
    }
}