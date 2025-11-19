package com.panabuntu.weathertracker.feature.core.di

import com.panabuntu.weathertracker.feature.core.data.remote_data_source.ForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.core.data.remote_data_source.ForecastDailyRemoteDataSourceImpl
import com.panabuntu.weathertracker.feature.core.data.repository.ForecastDailyRepositoryImpl
import com.panabuntu.weathertracker.feature.core.repository.ForecastDailyRepository
import com.panabuntu.weathertracker.feature.core.usecase.GetDayForecastDetailUseCase
import com.panabuntu.weathertracker.feature.core.usecase.GetDayForecastListUseCase
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.ForecastDayDetailViewModel
import com.panabuntu.weathertracker.feature.forecast_day_list.presentation.forecast_day_list.ForecastDailyViewModel
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
    factoryOf(::ForecastDailyViewModel)
    factoryOf(::ForecastDayDetailViewModel)
}

val forecastDailyModule = module {
    includes(dataModule, domainModule, presentationModule)
}