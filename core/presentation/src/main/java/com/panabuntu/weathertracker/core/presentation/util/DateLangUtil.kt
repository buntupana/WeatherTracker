package com.panabuntu.weathertracker.core.presentation.util

import com.panabuntu.weathertracker.core.presentation.R
import java.time.LocalDate

object DateLangUtil {

    fun getResIdNameOfDay(date: LocalDate): Int {
        val day = date.dayOfWeek.value
        val currentDate = LocalDate.now()
        return when {
            currentDate.dayOfWeek.value == day && currentDate.dayOfYear == date.dayOfYear -> R.string.core_today
            day == 1 -> R.string.core_monday
            day == 2 -> R.string.core_tuesday
            day == 3 -> R.string.core_wednesday
            day == 4 -> R.string.core_thursday
            day == 5 -> R.string.core_friday
            day == 6 -> R.string.core_saturday
            day == 7 -> R.string.core_sunday
            else -> R.string.core_sunday
        }
    }

    fun getResIdNameOfMonth(date: LocalDate): Int {
        return when (date.monthValue) {
            1 -> R.string.core_january_short
            2 -> R.string.core_february_short
            3 -> R.string.core_march_short
            4 -> R.string.core_april_short
            5 -> R.string.core_may_short
            6 -> R.string.core_june_short
            7 -> R.string.core_july_short
            8 -> R.string.core_august_short
            9 -> R.string.core_september_short
            10 -> R.string.core_october_short
            11 -> R.string.core_november_short
            12 -> R.string.core_december_short
            else -> R.string.core_january_short
        }
    }
}