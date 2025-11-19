package com.panabuntu.weathertracker.feature.forecast_daily.data.repository

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.data.util.DateUtils
import com.panabuntu.weathertracker.core.data.util.networkBoundResource
import com.panabuntu.weathertracker.core.data.util.networkBoundResourceList
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider
import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toDayForecastDetail
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toDayForecastEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toDayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.ForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.repository.ForecastDailyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ForecastDailyRepositoryImpl(
    private val remoteDataSource: ForecastDailyRemoteDataSource,
    private val database: AppDataBase,
    private val urlProvider: UrlProvider
) : ForecastDailyRepository {

    override suspend fun getDayListForecast(
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
                    it.toDayForecastSimple({ icon -> urlProvider.createIconUrl(icon = icon) })
                }
            },
            fetch = {
                remoteDataSource.getDailyForecast(lat = lat, lon = lon)
            },
            saveFetchResult = {
                database.dayForecastDao.upsertSimple(it.daily.toDayForecastEntity(lat = lat, lon = lon))
            }
        )
    }

    override suspend fun getDayForecast(
        date: Long,
        lat: Double,
        lon: Double
    ): Flow<Result<DayForecastDetail?, Error>> {

        return networkBoundResource(
            query = {
                database.dayForecastDao.getByLocationAndDate(
                    date = date,
                    lat = lat,
                    lon = lon
                ).map {
                    it?.toDayForecastDetail({ icon -> urlProvider.createIconUrl(icon = icon) })
                }
            },
            fetch = {
                remoteDataSource.getDailyForecast(lat = lat, lon = lon)
            },
            saveFetchResult = {
                database.dayForecastDao.upsertSimple(it.daily.toDayForecastEntity(lat = lat, lon = lon))
            }
        )
    }
}