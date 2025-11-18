package com.panabuntu.weathertracker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.navigation.NavRoutesMain
import com.panabuntu.weathertracker.core.presentation.snackbar.SnackbarController
import com.panabuntu.weathertracker.core.presentation.util.ObserveAsEvents
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.ForeCastDailyRoute
import com.panabuntu.weathertracker.navigation.ForeCastDailyRouteNav
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {

    private val navRoutesMain: NavRoutesMain by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            navRoutesMain.init(navController)

            val snackbarHostState = remember { SnackbarHostState() }

            val scope = rememberCoroutineScope()
            val context = LocalContext.current

            ObserveAsEvents(
                SnackbarController.events,
                snackbarHostState
            ) { event ->
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.action?.name,
                        duration = event.snackbarDuration
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        event.action?.action?.invoke()
                    }
                }
            }

            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = ForeCastDailyRoute
                    ) {
                        composable<ForeCastDailyRoute> {
                            ForeCastDailyRouteNav(navRoutesMain)
                        }
                    }
                }
            }
        }
    }
}