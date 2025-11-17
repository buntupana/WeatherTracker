package com.panabuntu.weathertracker.core.testing.dao

import com.panabuntu.weathertracker.core.data.dao.DailyDao
import com.panabuntu.weathertracker.core.data.entity.DailyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.filter
import kotlin.collections.filterNot
import kotlin.collections.plus

class FakeForecastDailyDao : DailyDao {

    private val items = MutableStateFlow<List<DailyEntity>>(emptyList())

    override suspend fun upsertAll(daily: List<DailyEntity>) {
        items.update { current ->
            val updated = current
                .filterNot { old -> daily.any { it.date == old.date } }
                .plus(daily)
                .sortedBy { it.date }

            updated
        }
    }

    override fun getAll(): Flow<List<DailyEntity>> {
        return items
    }

    override suspend fun deleteOlderThan(timestamp: Long) {
        items.update { it.filter { it.date >= timestamp } }
    }

    override suspend fun clearAll() {
        items.update { emptyList() }
    }
}