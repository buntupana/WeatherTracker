package com.panabuntu.weathertracker.utils

import com.panabuntu.weathertracker.core.domain.AppLogger
import timber.log.Timber

class TimberLogger : AppLogger {

    companion object {
        private val TAG: String
            get() {
                val stackTrace = Throwable().stackTrace
                val stackTraceElement = stackTrace[3]

                return "(${stackTraceElement.fileName}:${stackTraceElement.lineNumber})#${stackTraceElement.methodName}"
            }
    }

    override fun d(message: String) {
        Timber.tag(TAG).d(message)
    }

    override fun e(throwable: Throwable, message: String?) {
        if (message == null) {
            Timber.tag(TAG).e(throwable)
        } else {
            Timber.tag(TAG).e(throwable, message)
        }
    }

    override fun w(throwable: Throwable, message: String?) {
        if (message == null) {
            Timber.tag(TAG).w(throwable)
        } else {
            Timber.tag(TAG).w(throwable, message)
        }
    }
}