package com.panabuntu.weathertracker.core.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class AppDimensions(
    val paddingSmall: Float = 4f,
    val paddingMedium: Float = 8f,
    val paddingLarge: Float = 16f,
    val paddingBig: Float = 16f,
    val paddingHuge: Float = 24f,
    val cornerRadius: Float = 12f,
    val spacingXs: Float = 2f,
    val spacingSm: Float = 4f,
    val spacingMd: Float = 8f,
    val spacingLg: Float = 16f,
    val spacingXl: Float = 24f,

    val iconForecastList: Float = 80f,
    val iconTopBar: Float = 60f,

    val forecastIcon: Float = 40f
)

val LocalAppDimens = staticCompositionLocalOf { AppDimensions() }