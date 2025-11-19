package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.theme.LocalAppDimens
import com.panabuntu.weathertracker.core.presentation.util.SetSystemBarsColors
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForecastDayDetailScreen(
    viewModel: ForecastDayDetailViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ForecastDayDetailContent(
        state = state,
        onIntent = { intent ->
            viewModel.onIntent(intent)
        }
    )
}

@Composable
private fun ForecastDayDetailContent(
    state: ForecastDayDetailState,
    onIntent: (ForecastDayDetailIntent) -> Unit
) {
    val dimens = LocalAppDimens.current

    SetSystemBarsColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navigationBarColor = MaterialTheme.colorScheme.background
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
//            ForecastDailyTopBar(locationName = state.locationName)
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
    AppTheme {
        ForecastDayDetailContent(
            state = ForecastDayDetailState(),
            onIntent = {}
        )
    }
}