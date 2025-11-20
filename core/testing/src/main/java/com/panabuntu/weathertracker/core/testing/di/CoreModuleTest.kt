package com.panabuntu.weathertracker.core.testing.di

import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.data.provider.UrlProviderImpl
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider
import com.panabuntu.weathertracker.core.presentation.navigation.NavArgsProvider
import com.panabuntu.weathertracker.core.testing.database.FakeAppDataBase
import com.panabuntu.weathertracker.core.testing.presentation.FakeNavArgsProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModuleTest = module {
    single { FakeAppDataBase() } bind AppDataBase::class
    singleOf(::UrlProviderImpl) bind UrlProvider::class
    singleOf(::FakeNavArgsProvider) bind NavArgsProvider::class
}