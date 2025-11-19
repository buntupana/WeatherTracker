package com.panabuntu.weathertracker.feature.forecast_hourly.data.repository

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.data.util.DateUtils
import com.panabuntu.weathertracker.core.data.util.networkBoundResource
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider
import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.core.domain.util.toStartOfDayUtc
import com.panabuntu.weathertracker.feature.forecast_hourly.data.mapper.toEntity
import com.panabuntu.weathertracker.feature.forecast_hourly.data.mapper.toModel
import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.ForecastHourlyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.dto.ForecastDayDetailDto
import com.panabuntu.weathertracker.feature.forecast_hourly.domain.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.forecast_hourly.domain.repository.ForecastDayDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ForecastDayDetailRepositoryImpl(
    private val database: AppDataBase,
    private val remoteDataSource: ForecastHourlyRemoteDataSource,
    private val urlProvider: UrlProvider
) : ForecastDayDetailRepository {

    override suspend fun getDayDetail(
        lat: Double,
        lon: Double
    ): Flow<Result<DayForecastDetail?, Error>> {

        return networkBoundResource<ForecastDayDetailDto, DayForecastDetail?>(
            query = {
                database.dayForecastDao.getDayWithHours(
                    date = DateUtils.getCurrentDayTimestampUTC(),
                    lat = lat,
                    lon = lon
                ).map {
                    it?.toModel { icon -> urlProvider.createIconUrl(icon) }
                }
            },
            fetch = {
                remoteDataSource.getDayDetailForecast(lat = lat, lon = lon)
            },
            saveFetchResult = {
                val startOfTheDay = it.current.dt.toStartOfDayUtc()

                val day = it.current.toEntity(date = startOfTheDay, lat = lat, lon = lon)
                val hourList =
                    it.hourly.toEntity(dateStartOfDay = startOfTheDay, lat = lat, lon = lon)

                database.dayForecastDao.update(day)
                database.hourForecastDao.upsert(hourList)
            }
        )
    }
}