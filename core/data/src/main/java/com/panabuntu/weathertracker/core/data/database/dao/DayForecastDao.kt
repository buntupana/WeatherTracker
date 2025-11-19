package com.panabuntu.weathertracker.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayForecastDao {

    @Upsert(entity = DayForecastEntity::class)
    suspend fun upsertSimple(dayEntity: List<DayForecastEntity>)

    @Query(
        """
        SELECT * FROM day_forecast 
        WHERE lat = :lat  AND lon = :lon ORDER BY date ASC LIMIT :limit
    """
    )
    fun getByLocation(lat: Double, lon: Double, limit: Int): Flow<List<DayForecastEntity>>

    @Query(
        """
        SELECT * FROM day_forecast 
        WHERE date = :date AND lat = :lat  AND lon = :lon 
        ORDER BY date ASC
    """
    )
    fun getByLocationAndDate(
        date: Long,
        lat: Double,
        lon: Double,
    ): Flow<DayForecastEntity?>

    @Query("DELETE FROM day_forecast WHERE date < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)

    @Query("DELETE FROM day_forecast")
    suspend fun clearAll()
}