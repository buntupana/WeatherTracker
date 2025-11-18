package com.panabuntu.weathertracker.core.presentation.navigation

import androidx.navigation.NavType
import kotlin.reflect.KType

interface NavTypeMap {
    val typeMap: Map<KType, NavType<*>>
}