package com.panabuntu.weathertracker.feature.forecast_daily.data.dummy_data

import com.panabuntu.weathertracker.core.data.database.entity.DayForecastEntity
import com.panabuntu.weathertracker.core.domain.Const
import java.time.LocalDate
import java.time.ZoneOffset

object DailyEntityDummy {

    fun getDailyEntityList(numberOfItems: Int = Const.DEFAULT_NUMBER_DAILY_ITEMS, startFromLocalDate: LocalDate = LocalDate.now()): List<DayForecastEntity> {

        return (0..<numberOfItems).map {
            val date = startFromLocalDate
                .plusDays(it.toLong())
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC)
                .epochSecond

            DayForecastEntity(
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