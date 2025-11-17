package com.panabuntu.weathertracker.core.testing.di

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.testing.database.FakeAppDataBase
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModuleTest = module {
    single{ FakeAppDataBase() } bind AppDataBase::class
}