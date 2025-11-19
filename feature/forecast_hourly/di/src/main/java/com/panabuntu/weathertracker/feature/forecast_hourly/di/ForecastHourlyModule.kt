package com.panabuntu.weathertracker.feature.forecast_hourly.di

import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.ForecastHourlyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_hourly.data.remote_data_source.ForecastHourlyRemoteDataSourceImpl
import com.panabuntu.weathertracker.feature.forecast_hourly.data.repository.ForecastDayDetailRepositoryImpl
import com.panabuntu.weathertracker.feature.forecast_hourly.domain.repository.ForecastDayDetailRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::ForecastHourlyRemoteDataSourceImpl) bind ForecastHourlyRemoteDataSource::class
    singleOf(::ForecastDayDetailRepositoryImpl) bind ForecastDayDetailRepository::class
}

private val domainModule = module {
}

private val presentationModule = module {
}

val forecastHourlyModule = module {
    includes(dataModule, domainModule, presentationModule)
}