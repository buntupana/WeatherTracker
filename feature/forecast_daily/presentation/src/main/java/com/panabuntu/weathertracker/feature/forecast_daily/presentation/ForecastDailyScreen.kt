package com.panabuntu.weathertracker.feature.forecast_daily.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.panabuntu.weathertracker.core.presentation.util.SetSystemBarsColors
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.DayForecastCard
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.comp.ForecastDailyTopBar
import com.panabuntu.weathertracker.forecast_list.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForecastDailyScreen(
    viewModel: ForecastDailyViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ForecastDailyContent(
        state = state,
        onIntent = {
            viewModel.onIntent(it)
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
            ForecastDailyTopBar()
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateStartPadding(LayoutDirection.Rtl),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
                )
        ) {

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (state.isLoadingError) {
                ErrorAndRetry(
                    modifier = Modifier.fillMaxSize(),
                    errorMessage = state.errorMessage?.asString().orEmpty(),
                    onRetryClick = {
                        onIntent(ForecastDailyIntent.GetDailyForecast)
                    }
                )
            }

            if (state.dailyList.isNullOrEmpty()) return@Column

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
                        item = item
                    )

                    Spacer(modifier = Modifier.size(dimens.paddingLarge.dp))
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
                isLoadingError = true,
                errorMessage = UiText.StringResource(R.string.forecast_daily_error_refreshing_daily_data),
                dailyList = listOf()
            ),
            onIntent = {}
        )
    }
}