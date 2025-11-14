package com.panabuntu.weathertracker.utils

import timber.log.Timber

class MyDebugTree : Timber.DebugTree() {
    /**
     * Creates a custom tag for the log message.
     * The tag will be the class name from which the log was called,
     * linked as a clickable hyperlink in Logcat.
     */
    override fun createStackElementTag(element: StackTraceElement): String {
        return "(${element.fileName}:${element.lineNumber}) #${element.methodName}"
    }
}