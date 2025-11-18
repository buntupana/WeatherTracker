package com.panabuntu.weathertracker.core.domain.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Throws(DateTimeParseException::class)
fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochSecond(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

@OptIn(ExperimentalContracts::class)
fun CharSequence?.isNotNullOrBlank(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrBlank != null)
    }
    return this.isNullOrBlank().not()
}