package com.panabuntu.weathertracker.feature.core.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.domain.util.toUTCStartOfDayTimestamp
import com.panabuntu.weathertracker.feature.core.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.core.usecase.GetDayForecastListUseCase
import com.panabuntu.weathertracker.feature.forecast_day_list.presentation.forecast_day_list.ForecastDailyState
import com.panabuntu.weathertracker.feature.forecast_day_list.presentation.forecast_day_list.ForecastDailyViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastDailyViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    lateinit var logger: AppLogger

    @Mock
    lateinit var getDayForecastListUseCase: GetDayForecastListUseCase

    private lateinit var viewModel: ForecastDailyViewModel

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

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ForecastDailyViewModel(logger, getDayForecastListUseCase)
    }

    @Test
    fun `calls GetDailyForecastList on first state subscription`() = runTest {

        whenever(getDayForecastListUseCase(any(), any())).thenReturn(
            flow { emit(Result.Error(NetworkError.SERVER_ERROR)) }
        )

        viewModel.state.test {

            val initialEmission = awaitItem()

            assertThat(initialEmission.isLoading).isFalse()
            assertThat(initialEmission.isRefreshing).isFalse()
            assertThat(initialEmission.dailyList).isEmpty()

            // loading emission
            awaitItem()

            // result emission
            val finalEmission = awaitItem()

            assertThat(finalEmission.isLoading).isFalse()
            assertThat(finalEmission.isRefreshing).isFalse()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }

    @Test
    fun `set loading state before calling GetDailyForecast`() = runTest {

        val finalState = ForecastDailyState(
            lat = Const.DEFAULT_LAT,
            lon = Const.DEFAULT_LON,
            isLoading = true,
            isRefreshing = false,
            locationName = Const.DEFAULT_LOCATION_NAME
        )

        whenever(getDayForecastListUseCase(any(), any())).thenReturn(
            flow { emit(Result.Error(NetworkError.SERVER_ERROR)) }
        )

        viewModel.state.test {

            // skipping initial
            skipItems(1)
            val emission = awaitItem()

            assertThat(emission).isEqualTo(finalState)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `emits dailyList when fetch is successful`() = runTest(testDispatcher) {

        whenever(getDayForecastListUseCase(any(), any())).thenReturn(
            flow { emit(Result.Success(dayForecastSimpleLists)) }
        )

        viewModel.state.test {

            // Skip initial state and loading state
            skipItems(2)

            val emission = awaitItem()

            assertThat(emission.dailyList.size).isEqualTo(dayForecastSimpleLists.size)

            assertThat(emission.isLoading).isFalse()
            assertThat(emission.isRefreshing).isFalse()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }

    @Test
    fun `emits dailyList when fetch is successful but list is empty`() = runTest(testDispatcher) {

        whenever(getDayForecastListUseCase(any(), any()))
            .thenReturn(flow { emit(Result.Success(emptyList())) })

        viewModel.state.test {

            // Skip initial state and loading state
            skipItems(2)

            val emission = awaitItem()

            // when list is empty, dailyList should be null if there wasn't any previous data
            assertThat(emission.isLoading).isFalse()
            assertThat(emission.isRefreshing).isFalse()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }

    @Test
    fun `emits dailyList when fetch is successful but list is empty and previous data was not null`() =
        runTest(testDispatcher) {

            whenever(getDayForecastListUseCase(any(), any()))
                .thenReturn(
                    flow {
                        emit(Result.Success(dayForecastSimpleLists))
                        emit(Result.Success(emptyList()))
                    }
                )

            viewModel.state.test {

                // Skip initial state and loading state
                skipItems(2)

                val emission = awaitItem()

                assertThat(emission.dailyList).isNotNull()
                assertThat(emission.isLoading).isFalse()
                assertThat(emission.isRefreshing).isFalse()
            }

            verify(getDayForecastListUseCase).invoke(any(), any())
        }

    @Test
    fun `emits error when fetch fails and current list is empty`() = runTest(testDispatcher) {

        whenever(getDayForecastListUseCase(any(), any())).thenReturn(
            flow { emit(Result.Error(NetworkError.SERVER_ERROR)) }
        )

        viewModel.state.test {

            skipItems(2)

            val emission = awaitItem()

            assertThat(emission.isLoading).isFalse()
            assertThat(emission.isRefreshing).isFalse()
            assertThat(emission.dailyList).isEmpty()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }

    @Test
    fun `emits error when fetch fails and current list is not empty`() = runTest(testDispatcher) {
        whenever(getDayForecastListUseCase(any(), any())).thenReturn(
            flow {
                emit(Result.Success(dayForecastSimpleLists))
                emit(Result.Error(NetworkError.SERVER_ERROR))
            }
        )

        viewModel.state.test {

            skipItems(2)

            val emission = awaitItem()

            assertThat(emission.isLoading).isFalse()
            assertThat(emission.isRefreshing).isFalse()
            assertThat(emission.dailyList).isNotEmpty()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }
}