package com.panabuntu.weathertracker.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.panabuntu.weathertracker.core.data.entity.DailyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDao {

    @Upsert
    suspend fun upsertAll(daily: List<DailyEntity>)

    @Query("SELECT * FROM daily WHERE lat = :lat AND lon = :lon ORDER BY date ASC LIMIT :limit")
    fun getByLocation(lat: Double, lon: Double, limit: Int): Flow<List<DailyEntity>>

    @Query("DELETE FROM daily WHERE date < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)

    @Query("DELETE FROM daily")
    suspend fun clearAll()
}