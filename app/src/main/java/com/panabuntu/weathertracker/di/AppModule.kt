package com.panabuntu.weathertracker.di

import com.panabuntu.weathertracker.utils.TimberLogger
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::TimberLogger) bind AppLogger::class
}