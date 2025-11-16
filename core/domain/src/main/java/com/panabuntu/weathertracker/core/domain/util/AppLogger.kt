package com.panabuntu.weathertracker.core.domain.util

interface AppLogger {
    fun d(message: String)
    fun e(throwable: Throwable, message: String? = null)
    fun w(throwable: Throwable, message: String? = null)
}