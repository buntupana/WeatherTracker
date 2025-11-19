package com.panabuntu.weathertracker.feature.forecast_daily.di

import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.ForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.ForecastDailyRemoteDataSourceImpl
import com.panabuntu.weathertracker.feature.forecast_daily.data.repository.ForecastDailyRepositoryImpl
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.ForecastDayDetailViewModel
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list.ForecastDayListViewModel
import com.panabuntu.weathertracker.feature.forecast_daily.repository.ForecastDailyRepository
import com.panabuntu.weathertracker.feature.forecast_daily.usecase.GetDayForecastDetailUseCase
import com.panabuntu.weathertracker.feature.forecast_daily.usecase.GetDayForecastListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::ForecastDailyRepositoryImpl) bind ForecastDailyRepository::class
    singleOf(::ForecastDailyRemoteDataSourceImpl) bind ForecastDailyRemoteDataSource::class
}

private val domainModule = module {
    factoryOf(::GetDayForecastListUseCase)
    factoryOf(::GetDayForecastDetailUseCase)
}

private val presentationModule = module {
    factoryOf(::ForecastDayListViewModel)
    factoryOf(::ForecastDayDetailViewModel)
}

val forecastDailyModule = module {
    includes(dataModule, domainModule, presentationModule)
}