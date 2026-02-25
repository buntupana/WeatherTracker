package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.panabuntu.weathertracker.core.presentation.R
import com.panabuntu.weathertracker.core.presentation.util.UiText
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list.comp.DayForecastItem
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class ForecastDailyTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun errorState_showsErrorMessageAndRetryButton() {
        val onIntent: (ForecastDayListIntent) -> Unit = mockk(relaxed = true)
        val errorMessage = context.getString(R.string.core_error_loading_data)
        val retryText = context.getString(R.string.core_retry)

        composeTestRule.setContent {
            ForecastDailyContent(
                state = ForecastDayListState(
                    isLoading = false,
                    dayForecastItemList = emptyList(),
                    locationName = "Madrid",
                    lat = 0.0,
                    lon = 0.0
                ),
                onIntent = onIntent
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText(retryText).assertIsDisplayed()
        
        composeTestRule.onNodeWithText(retryText).performClick()
        verify { onIntent(ForecastDayListIntent.GetDailyForecast) }
    }

    @Test
    fun successState_showsListItems() {
        val items = listOf(
            DayForecastItem(
                timestamp = 1625097600L,
                iconUrl = null,
                dayName = UiText.DynamicString("Monday"),
                dayOfMonth = 1,
                monthName = UiText.DynamicString("July"),
                maxTemp = "30°C",
                minTemp = "20°C",
                description = "Sunny"
            )
        )

        composeTestRule.setContent {
            ForecastDailyContent(
                state = ForecastDayListState(
                    isLoading = false,
                    dayForecastItemList = items,
                    locationName = "Madrid",
                    lat = 0.0,
                    lon = 0.0
                ),
                onIntent = {}
            )
        }

        composeTestRule.onNodeWithText("Monday").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sunny").assertIsDisplayed()
        composeTestRule.onNodeWithText("Madrid").assertIsDisplayed()
    }

    @Test
    fun clickingItem_triggersOnDayClickIntent() {
        val timestamp = 1625097600L
        val onIntent: (ForecastDayListIntent) -> Unit = mockk(relaxed = true)
        val items = listOf(
            DayForecastItem(
                timestamp = timestamp,
                iconUrl = null,
                dayName = UiText.DynamicString("Monday"),
                dayOfMonth = 1,
                monthName = UiText.DynamicString("July"),
                maxTemp = "30°C",
                minTemp = "20°C",
                description = "Sunny"
            )
        )

        composeTestRule.setContent {
            ForecastDailyContent(
                state = ForecastDayListState(
                    isLoading = false,
                    dayForecastItemList = items,
                    locationName = "Madrid",
                    lat = 0.0,
                    lon = 0.0
                ),
                onIntent = onIntent
            )
        }

        composeTestRule.onNodeWithText("Monday").performClick()

        verify { onIntent(ForecastDayListIntent.OnDayClick(timestamp)) }
    }
}
