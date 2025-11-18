package com.panabuntu.weathertracker.feature.forecast_daily.data.dummy_data

import com.panabuntu.weathertracker.core.data.entity.DailyEntity
import java.time.LocalDate
import java.time.ZoneOffset

object DailyEntityDummy {

    fun getDailyEntityList(numberOfItems: Int = 8, startFromLocalDate: LocalDate = LocalDate.now()): List<DailyEntity> {

        return (0..<numberOfItems).map {
            val date = startFromLocalDate
                .plusDays(it.toLong())
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC)
                .epochSecond

            DailyEntity(
                date = date,
                lat = 0.0,
                lon = 0.0,
                maxTemp = 22f,
                minTemp = 10f,
                icon = "01d",
                description = "test description"
            )
        }
    }
}