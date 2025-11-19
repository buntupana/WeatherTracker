package com.panabuntu.weathertracker.core.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DayWithHours(
    @Embedded val day: DayForecastEntity,

    @Relation(
        parentColumn = "date",
        entityColumn = "dateStartOfDay",
        entity = HourForecastEntity::class
    )
    val hours: List<HourForecastEntity>
)