package com.panabuntu.weathertracker.feature.forecast_daily.di

import com.panabuntu.weathertracker.feature.forecast_daily.data.repository.ForecastDailyRepositoryImpl
import com.panabuntu.weathertracker.feature.forecast_daily.repository.repository.ForecastListRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::ForecastDailyRepositoryImpl) bind ForecastListRepository::class
}

private val domainModule = module {

}

private val presentationModule = module {

}

val forecastDailyModule = module {
    includes(dataModule, domainModule, presentationModule)
}