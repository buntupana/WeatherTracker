package com.panabuntu.weathertracker.core.presentation.navigation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.serialization.generateHashCode
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.domain.util.encodeAllStrings
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.reflect.KClass

class NavRoutesMainImpl : NavRoutesMain, KoinComponent {

    private val logger: AppLogger by inject()

    private var navController: NavController? = null

    override fun init(navController: NavController): NavRoutesMainImpl {
        this.navController = navController
        return this
    }

    override fun handleDeepLink(intent: Intent) {
        logger.d("handleDeepLing() called with: intent = [$intent]")
        navController?.handleDeepLink(intent)
    }

    override fun navigate(
        destination: Route
    ) {
        navigate<Route>(
            destination = destination,
            popUpTo = null,
            popUpToInclusive = false
        )
    }

    override fun <T : Route> navigate(
        destination: Route,
        popUpTo: KClass<T>?,
        popUpToInclusive: Boolean
    ) {
        logger.d("navigate() called with: destination = [$destination], popUpTo = [$popUpTo], popUpToInclusive = [$popUpToInclusive]")
        navController?.navigate(destination.encodeAllStrings())
    }

    override fun popBackStack() {
        logger.d("popBackStack() called")
        navController?.popBackStack()
    }

    @SuppressLint("RestrictedApi")
    @OptIn(InternalSerializationApi::class)
    override fun <T : Route> popBackStack(
        route: KClass<in T>,
        inclusive: Boolean
    ) {
        navController?.popBackStack(route.serializer().generateHashCode(), inclusive)
    }

    override fun <T : Route> isCurrentDestination(destination: KClass<T>): Boolean {
        logger.d("isCurrentDestination() called with: destination = [$destination]")
        return navController?.currentDestination?.hasRoute(destination) ?: false
    }

    override fun saveResult(value: Any, key: String) {
        navController?.previousBackStackEntry?.savedStateHandle?.set(key, value)
    }

    override fun <T> getStateFlowResult(key: String): StateFlow<T?>? {
        return navController?.currentBackStackEntry?.savedStateHandle?.run {
            val result = getStateFlow<T?>(key, null)
            set(key, null)
            result
        }
    }

    override fun <T> getResult(key: String): T? {
        return navController?.currentBackStackEntry?.savedStateHandle?.run {
            val result = get<T>(key)
            remove<T>(key)
            result
        }
    }
}