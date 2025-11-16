package com.panabuntu.weathertracker.core.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
data class DailyEntity(
    @PrimaryKey
    val date: Long,
    val maxTemp: Float,
    val minTemp: Float,
    val description: String?,
    val icon: String?
)