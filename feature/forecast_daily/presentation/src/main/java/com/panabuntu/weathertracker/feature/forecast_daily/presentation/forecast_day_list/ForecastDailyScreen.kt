package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.presentation.comp.ErrorAndRetry
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.theme.LocalAppDimens
import com.panabuntu.weathertracker.core.presentation.util.SetSystemBarsColors
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.DayForecastCard
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.ForecastDailyTopBar
import com.panabuntu.weathertracker.forecast_list.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForecastDailyScreen(
    viewModel: ForecastDailyViewModel = koinViewModel(),
    onDayClick: (date: Long, lat: Double, lon: Double) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ForecastDailyContent(
        state = state,
        onIntent = { intent ->
            when (intent) {
                is ForecastDailyIntent.OnDayClick ->{
                    onDayClick(
                        intent.date,
                        state.lat,
                        state.lon
                    )
                }
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@Composable
private fun ForecastDailyContent(
    state: ForecastDailyState,
    onIntent: (ForecastDailyIntent) -> Unit
) {

    val dimens = LocalAppDimens.current

    SetSystemBarsColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navigationBarColor = MaterialTheme.colorScheme.background
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ForecastDailyTopBar(locationName = state.locationName)
        }
    ) { paddingValues ->

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateStartPadding(LayoutDirection.Rtl),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
                ),
            isRefreshing = state.isRefreshing,
            onRefresh = { onIntent(ForecastDailyIntent.GetDailyForecast) }
        ) {

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.dailyList.isEmpty() -> {
                    ErrorAndRetry(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimens.paddingLarge.dp),
                        errorMessage = stringResource(R.string.forecast_daily_error_loading_daily_data),
                        onRetryClick = {
                            onIntent(ForecastDailyIntent.GetDailyForecast)
                        }
                    )
                }
            }

            AnimatedVisibility(
                visible = state.dailyList.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        top = dimens.paddingLarge.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    )
                ) {

                    items(
                        count = state.dailyList.size,
                        key = { index ->
                            state.dailyList[index].timestamp
                        }
                    ) { index ->

                        val item = state.dailyList[index]

                        DayForecastCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = dimens.paddingLarge.dp),
                            item = item,
                            onClick = {
                                onIntent(ForecastDailyIntent.OnDayClick(item.timestamp))
                            }
                        )

                        Spacer(modifier = Modifier.size(dimens.paddingLarge.dp))
                    }
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun ForecastDailyScreenPreview() {
    AppTheme {
        ForecastDailyContent(
            state = ForecastDailyState(
                isLoading = false,
                dailyList = listOf(),
                locationName = Const.DEFAULT_LOCATION_NAME,
                lat = Const.DEFAULT_LAT,
                lon = Const.DEFAULT_LON
            ),
            onIntent = {}
        )
    }
}