package com.panabuntu.weathertracker.core.domain.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException

@Throws(DateTimeParseException::class)
fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochSecond(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}