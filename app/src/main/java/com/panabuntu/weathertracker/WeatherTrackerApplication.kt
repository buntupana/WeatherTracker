package com.panabuntu.weathertracker

import android.app.Application
import com.panabuntu.weathertracker.core.di.coreModule
import com.panabuntu.weathertracker.di.appModule
import com.panabuntu.weathertracker.feature.forecast_daily.di.forecastDailyModule
import com.panabuntu.weathertracker.feature.forecast_hourly.di.forecastHourlyModule
import com.panabuntu.weathertracker.utils.MyDebugTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(MyDebugTree())

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@WeatherTrackerApplication)
            // Load modules
            modules(
                appModule,
                coreModule,
                forecastDailyModule,
                forecastHourlyModule
            )
        }
    }
}