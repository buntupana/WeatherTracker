package com.panabuntu.weathertracker.feature.forecast_daily.data.repository

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.data.util.DateUtils
import com.panabuntu.weathertracker.core.data.util.networkBoundResourceList
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider
import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toModel
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.ForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.repository.ForecastDailyRepository
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
    ): Flow<Result<List<DayForecastSimple>, Error>> {

        return networkBoundResourceList(
            query = {
                database.dayForecastDao.deleteOlderThan(DateUtils.getCurrentDayTimestampUTC())
                database.dayForecastDao.getByLocation(
                    lat = lat,
                    lon = lon,
                    limit = Const.DEFAULT_NUMBER_DAILY_ITEMS
                ).map {
                    it.toModel({ icon -> urlProvider.createIconUrl(icon = icon) })
                }
            },
            fetch = {
                remoteDataSource.getDailyForecast(lat = lat, lon = lon)
            },
            saveFetchResult = {
                database.dayForecastDao.upsertSimple(it.daily.toEntity(lat = lat, lon = lon))
            }
        )
    }
}