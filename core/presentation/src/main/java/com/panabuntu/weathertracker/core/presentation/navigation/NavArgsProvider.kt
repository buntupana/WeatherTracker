package com.panabuntu.weathertracker.core.presentation.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import kotlin.reflect.KClass
import kotlin.reflect.KType

interface NavArgsProvider {
    fun <T : Route> provideArg(
        route: KClass<T>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    ): T

    fun provideOriginalHandle(): SavedStateHandle
}