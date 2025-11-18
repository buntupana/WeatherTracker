package com.panabuntu.weathertracker.feature.forecast_daily.data.repository

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.data.util.DateUtils
import com.panabuntu.weathertracker.core.data.util.networkBoundResource
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider
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
    private val database: AppDataBase,
    private val urlProvider: UrlProvider
) : ForecastDailyRepository {

    override suspend fun getDaily(
        lat: Double,
        lon: Double
    ): Flow<Result<List<Daily>, Error>> {

        return networkBoundResource(
            query = {
                database.dailyDao.deleteOlderThan(DateUtils.getCurrentDayTimestampUTC())
                database.dailyDao.getByLocation(lat = lat, lon = lon).map {
                    it.toModel({ icon -> urlProvider.createIconUrl(icon = icon) })
                }
            },
            fetch = {
                remoteDataSource.getDailyForecast(lat = lat, lon = lon)
            },
            saveFetchResult = {
                database.dailyDao.upsertAll(it.daily.toEntity(lat = lat, lon = lon))
            }
        )
    }
}