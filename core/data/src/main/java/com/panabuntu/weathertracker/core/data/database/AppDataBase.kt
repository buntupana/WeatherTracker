package com.panabuntu.weathertracker.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.panabuntu.weathertracker.core.data.database.dao.DayForecastDao
import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity
import com.panabuntu.weathertracker.core.data.database.entity.HourForecastEntity

@Database(
    entities = [
        DayForecastEntity::class,
        HourForecastEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDataBase : RoomDatabase() {

    companion object Companion {
        fun newInstance(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context = context,
                klass = AppDataBase::class.java,
                name = "app-database"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract val dayForecastDao: DayForecastDao

}