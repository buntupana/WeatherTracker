package com.panabuntu.weathertracker.feature.forecast_daily.presentation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForecastDailyScreen(
    viewModel: ForecastDailyViewModel = koinViewModel()
) {
//    ForecastDailyContent(
//        state = viewModel.state
//    )
}

@Composable
private fun ForecastDailyContent(
    state: ForecastDailyState
) {

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
            ForecastDailyState()
        )
    }
}