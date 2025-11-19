package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.domain.util.toUTCStartOfDayTimestamp
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list.ForecastDayListViewModel
import com.panabuntu.weathertracker.feature.forecast_daily.usecase.GetDayForecastListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastDayListViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var logger: AppLogger

    @Mock
    lateinit var getDayForecastListUseCase: GetDayForecastListUseCase

    private lateinit var viewModel: ForecastDayListViewModel

    private val dayForecastSimpleLists: List<DayForecastSimple>
        get() {
            val currentDate = LocalDate.now()
            return (0..6).map { index ->
                DayForecastSimple(
                    timestamp = currentDate.toUTCStartOfDayTimestamp(),
                    date = currentDate.plusDays(index.toLong()),
                    maxTemp = 23f,
                    minTemp = 12f,
                    description = "description",
                    iconUrl = null
                )
            }
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = ForecastDayListViewModel(
            logger = logger,
            getDayForecastListUseCase = getDayForecastListUseCase
        )
    }
}