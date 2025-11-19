package com.panabuntu.weathertracker.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.panabuntu.weathertracker.core.data.database.entity.HourForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HourForecastDao {

    @Upsert
    suspend fun upsert(daily: List<HourForecastEntity>)

    @Query("""
        SELECT * FROM hour_forecast 
        WHERE lat = :lat AND lon = :lon 
        ORDER BY date 
        ASC LIMIT :limit
    """)
    fun getByLocation(lat: Double, lon: Double, limit: Int): Flow<List<HourForecastEntity>>

    @Query("DELETE FROM hour_forecast WHERE date < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)

    @Query("DELETE FROM hour_forecast")
    suspend fun clearAll()
}