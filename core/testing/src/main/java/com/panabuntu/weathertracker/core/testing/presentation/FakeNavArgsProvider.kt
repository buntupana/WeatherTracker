package com.panabuntu.weathertracker.core.testing.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import com.panabuntu.weathertracker.core.presentation.navigation.NavArgsProvider
import com.panabuntu.weathertracker.core.presentation.navigation.Route
import kotlin.reflect.KClass
import kotlin.reflect.KType

class FakeNavArgsProvider: NavArgsProvider {

    var resultRoute: Route? = null

    override fun <T : Route> provideArg(
        route: KClass<T>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>
    ): T {
        return resultRoute as T
    }


    override fun provideOriginalHandle(): SavedStateHandle {
        return SavedStateHandle()
    }
}