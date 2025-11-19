package com.panabuntu.weathertracker.core.testing.dao

import androidx.compose.ui.util.fastFirstOrNull
import com.panabuntu.weathertracker.core.data.database.dao.DayForecastDao
import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeForecastDailyDao : DayForecastDao {

    private val items = MutableStateFlow<List<DayForecastEntity>>(emptyList())

    override suspend fun upsertSimple(dayEntity: List<DayForecastEntity>) {
        items.update { current ->
            val updated = current
                .filterNot { old -> dayEntity.any { it.date == old.date } }
                .plus(dayEntity)
                .sortedBy { it.date }

            updated
        }
    }

    override fun getByLocation(
        lat: Double,
        lon: Double,
        limit: Int,
    ): Flow<List<DayForecastEntity>> {
        return items.map {
            it.filter { entity -> entity.lat == lat && entity.lon == lon }.sortedBy { entity -> entity.date }.take(limit)
        }
    }

    override fun getByLocationAndDate(
        date: Long,
        lat: Double,
        lon: Double
    ): Flow<DayForecastEntity?> {
        return items.map { list ->
            list.fastFirstOrNull { item -> item.lat == lat && item.lon == lon && item.date == date }
        }
    }

    override suspend fun deleteOlderThan(timestamp: Long) {
        items.update { entity -> entity.filter { it.date >= timestamp } }
    }

    override suspend fun clearAll() {
        items.update { emptyList() }
    }
}