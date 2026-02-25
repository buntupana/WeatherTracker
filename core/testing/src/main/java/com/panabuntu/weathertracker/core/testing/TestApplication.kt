package com.panabuntu.weathertracker.core.testing

import android.app.Application
import com.panabuntu.weathertracker.core.testing.di.coreUiModuleTest
import org.koin.core.context.GlobalContext.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(coreUiModuleTest)
        }
    }
}