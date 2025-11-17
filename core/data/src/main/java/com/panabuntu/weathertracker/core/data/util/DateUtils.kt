package com.panabuntu.weathertracker.core.data.util

import java.time.LocalDate
import java.time.ZoneOffset

object DateUtils {

    fun getCurrentDayTimestampUTC() = LocalDate.now(ZoneOffset.UTC)
        .atStartOfDay()
        .toInstant(ZoneOffset.UTC)
        .epochSecond
}