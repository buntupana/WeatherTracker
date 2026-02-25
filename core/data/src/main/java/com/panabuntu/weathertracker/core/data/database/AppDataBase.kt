package com.panabuntu.weathertracker.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.panabuntu.weathertracker.core.data.database.dao.DayForecastDao
import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity

@Database(
    entities = [
        DayForecastEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDataBase : RoomDatabase() {

    companion object Companion {

        private const val DATABASE_NAME = "app-database"

        fun newInstance(context: Context, inMemory: Boolean = false): AppDataBase {
            return if (inMemory) {
                Room.inMemoryDatabaseBuilder(
                    context = context,
                    klass = AppDataBase::class.java
                ).build()
            } else {
                Room.databaseBuilder(
                    context = context,
                    klass = AppDataBase::class.java,
                    name = DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
            }
        }
    }

    abstract val dayForecastDao: DayForecastDao

}