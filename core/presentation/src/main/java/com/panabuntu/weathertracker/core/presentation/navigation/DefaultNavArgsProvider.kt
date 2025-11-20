package com.panabuntu.weathertracker.core.presentation.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.panabuntu.weathertracker.core.domain.util.decodeAllStrings
import kotlin.reflect.KClass
import kotlin.reflect.KType

class DefaultNavArgsProvider(
    private val savedStateHandle: SavedStateHandle
) : NavArgsProvider {
    override fun <T : Route> provideArg(
        route: KClass<T>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>
    ): T {
        return savedStateHandle.toRoute(route = route).decodeAllStrings()
    }

    override fun provideOriginalHandle(): SavedStateHandle {
        return savedStateHandle
    }
}