package com.panabuntu.weathertracker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.navigation.NavRoutesMain
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.ForeCastDailyRoute
import com.panabuntu.weathertracker.navigation.ForeCastDailyRouteNav
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

            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
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