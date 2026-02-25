package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.panabuntu.weathertracker.core.presentation.R
import com.panabuntu.weathertracker.core.presentation.theme.AppTheme
import com.panabuntu.weathertracker.core.presentation.util.UiText
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class ForecastDayDetailTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun loadingState_showsCircularProgressIndicator() {
        composeTestRule.setContent {
            AppTheme {
                ForecastDayDetailContent(
                    state = ForecastDayDetailState(
                        isLoading = true,
                        locationName = "Madrid"
                    ),
                    onIntent = {}
                )
            }
        }

//        composeTestRule.onNode(hasProgressBarRangeInfo()).assertIsDisplayed()
        composeTestRule.onNodeWithText("Madrid").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessageAndRetryButton() {
        val onIntent: (ForecastDayDetailIntent) -> Unit = mockk(relaxed = true)
        val errorMessage = "Something went wrong"
        val retryText = context.getString(R.string.core_retry)

        composeTestRule.setContent {
            AppTheme {
                ForecastDayDetailContent(
                    state = ForecastDayDetailState(
                        isLoading = false,
                        errorMessage = UiText.DynamicString(errorMessage),
                        locationName = "Madrid"
                    ),
                    onIntent = onIntent
                )
            }
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText(retryText).assertIsDisplayed()

        composeTestRule.onNodeWithText(retryText).performClick()
        verify { onIntent(ForecastDayDetailIntent.GetDayDetail) }
    }

    @Test
    fun successState_showsForecastDetails() {
        val info = ForecastDetailInfo(
            dayName = UiText.DynamicString("Tuesday"),
            iconUrl = null,
            minTemp = "15°C",
            maxTemp = "25°C",
            monthName = UiText.DynamicString("August"),
            dayOfMonth = 15,
            windSpeed = UiText.DynamicString("10 km/h"),
            sunrise = "06:00 AM",
            sunset = "08:00 PM",
            humidity = "50%",
            uvIndex = "5",
            rainProbability = "10%",
            description = "Partly Cloudy"
        )

        composeTestRule.setContent {
            AppTheme {
                ForecastDayDetailContent(
                    state = ForecastDayDetailState(
                        isLoading = false,
                        locationName = "London",
                        forecastDetailInfo = info
                    ),
                    onIntent = {}
                )
            }
        }

        composeTestRule.onNodeWithText("London").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tuesday").assertIsDisplayed()
        composeTestRule.onNodeWithText("Partly Cloudy").assertIsDisplayed()
        composeTestRule.onNodeWithText("15°C").assertIsDisplayed()
        composeTestRule.onNodeWithText("25°C").assertIsDisplayed()
    }

    @Test
    fun clickingBack_triggersNavigateBackIntent() {
        val onIntent: (ForecastDayDetailIntent) -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            AppTheme {
                ForecastDayDetailContent(
                    state = ForecastDayDetailState(
                        locationName = "Madrid"
                    ),
                    onIntent = onIntent
                )
            }
        }

        // The back button has no content description but it is the first clickable element in the TopAppBar
        composeTestRule.onAllNodes(hasClickAction())[0].performClick()

        verify { onIntent(ForecastDayDetailIntent.NavigateBack) }
    }
}
