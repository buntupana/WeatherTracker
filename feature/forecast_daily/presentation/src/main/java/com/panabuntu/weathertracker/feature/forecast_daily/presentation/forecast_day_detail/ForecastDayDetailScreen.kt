package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.panabuntu.weathertracker.core.presentation.comp.ErrorAndRetry
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.theme.LocalAppDimens
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.comp.ForecastContent
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail.comp.ForecastDayDetailTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForecastDayDetailScreen(
    viewModel: ForecastDayDetailViewModel = koinViewModel(),
    navigateBackClick: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ForecastDayDetailContent(
        state = state,
        onIntent = { intent ->
            when (intent) {
                ForecastDayDetailIntent.NavigateBack -> navigateBackClick()
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@Composable
private fun ForecastDayDetailContent(
    state: ForecastDayDetailState,
    onIntent: (ForecastDayDetailIntent) -> Unit
) {
    val dimens = LocalAppDimens.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ForecastDayDetailTopBar(
                locationName = state.locationName,
                onBackClick = {
                    onIntent(ForecastDayDetailIntent.NavigateBack)
                }
            )
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
            onRefresh = { onIntent(ForecastDayDetailIntent.GetDayDetail) }
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

                state.errorMessage != null -> {
                    ErrorAndRetry(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimens.paddingLarge.dp),
                        errorMessage = state.errorMessage.asString(),
                        onRetryClick = {
                            onIntent(ForecastDayDetailIntent.GetDayDetail)
                        }
                    )
                    return@PullToRefreshBox
                }
            }

            state.forecastDetailInfo ?: return@PullToRefreshBox

            ForecastContent(
                modifier = Modifier.fillMaxSize(),
                forecast = state.forecastDetailInfo
            )
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
private fun ForecastDayDetailScreenPreview() {

    val info = ForecastDetailInfo(
        dayName = UiText.DynamicString("Today"),
        iconUrl = "",
        minTemp = "12°",
        maxTemp = "23°",
        monthName = UiText.DynamicString("January"),
        windSpeed = UiText.DynamicString("12 km/h"),
        sunrise = "12:00",
        sunset = "12:00",
        humidity = "12%",
        uvIndex = "12",
        rainProbability = "12%",
        description = "Clear Sky",
        dayOfMonth = 12
    )

    AppTheme {
        ForecastDayDetailContent(
            state = ForecastDayDetailState(
                isLoading = false,
                isRefreshing = false,
//                errorMessage = UiText.DynamicString("test"),
                errorMessage = null,
                locationName = "Madrid",
                forecastDetailInfo = info
            ),
            onIntent = {}
        )
    }
}