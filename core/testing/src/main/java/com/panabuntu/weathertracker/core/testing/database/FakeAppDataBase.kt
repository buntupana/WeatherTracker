package com.panabuntu.weathertracker.core.testing.database

import androidx.room.InvalidationTracker
import com.panabuntu.weathertracker.core.data.dao.DailyDao
import com.panabuntu.weathertracker.core.data.database.AppDataBase
import com.panabuntu.weathertracker.core.testing.dao.FakeForecastDailyDao

class FakeAppDataBase(
    override val dailyDao: DailyDao = FakeForecastDailyDao()
) : AppDataBase() {
    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}