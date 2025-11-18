package com.panabuntu.weathertracker.feature.forecast_daily.data.di

import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.FakeForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.ForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.data.repository.ForecastDailyRepositoryImpl
import com.panabuntu.weathertracker.feature.forecast_daily.repository.repository.ForecastDailyRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val forecastDailyModuleTest = module {
    singleOf(::FakeForecastDailyRemoteDataSource) bind ForecastDailyRemoteDataSource::class
    single<ForecastDailyRepository> {
        ForecastDailyRepositoryImpl(
            remoteDataSource = get(),
            database = get(),
            urlProvider = get()
        )
    }
}