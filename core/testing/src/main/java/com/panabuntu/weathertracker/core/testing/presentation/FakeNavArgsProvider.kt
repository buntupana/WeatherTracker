package com.panabuntu.weathertracker.core.testing.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import com.panabuntu.weathertracker.core.presentation.navigation.NavArgsProvider
import com.panabuntu.weathertracker.core.presentation.navigation.Route
import kotlin.reflect.KClass
import kotlin.reflect.KType

class FakeNavArgsProvider: NavArgsProvider {

    var resultRoute: Route? = null

    @Suppress("UNCHECKED_CAST")
    override fun <T : Route> provideArg(
        route: KClass<T>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>
    ): T {
        return resultRoute as T
    }


    @SuppressLint("VisibleForTests")
    override fun provideOriginalHandle(): SavedStateHandle {
        return SavedStateHandle()
    }
}