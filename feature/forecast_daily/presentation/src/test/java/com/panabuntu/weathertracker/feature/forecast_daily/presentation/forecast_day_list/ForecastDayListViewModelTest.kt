package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_list

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.domain.util.toUTCStartOfDayTimestamp
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.usecase.GetDayForecastListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
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

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = ForecastDayListViewModel(
            logger = logger,
            getDayForecastListUseCase = getDayForecastListUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `calls GetDailyForecastList on first state subscription`() = runTest {

        whenever(getDayForecastListUseCase(any(), any())).thenReturn(
            flow { emit(Result.Error(NetworkError.SERVER_ERROR)) }
        )

        viewModel.state.test {

            val initialEmission = awaitItem()

            Truth.assertThat(initialEmission.isLoading).isFalse()
            Truth.assertThat(initialEmission.isRefreshing).isFalse()
            Truth.assertThat(initialEmission.dayForecastItemList).isEmpty()

            // loading emission
            awaitItem()

            // result emission
            val finalEmission = awaitItem()

            Truth.assertThat(finalEmission.isLoading).isFalse()
            Truth.assertThat(finalEmission.isRefreshing).isFalse()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }

    @Test
    fun `set loading state before calling getDayForecastListUseCase`() = runTest {

        val finalState = ForecastDayListState(
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

            Truth.assertThat(emission).isEqualTo(finalState)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `emits dayForecastItemList when fetch is successful`() = runTest(testDispatcher) {

        whenever(getDayForecastListUseCase(any(), any())).thenReturn(
            flow { emit(Result.Success(dayForecastSimpleLists)) }
        )

        viewModel.state.test {

            // Skip initial state and loading state
            skipItems(2)

            val emission = awaitItem()

            Truth.assertThat(emission.dayForecastItemList.size).isEqualTo(dayForecastSimpleLists.size)

            Truth.assertThat(emission.isLoading).isFalse()
            Truth.assertThat(emission.isRefreshing).isFalse()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }

    @Test
    fun `emits dayForecastItemList when fetch is successful but list is empty`() = runTest(testDispatcher) {

        whenever(getDayForecastListUseCase(any(), any()))
            .thenReturn(flow { emit(Result.Success(emptyList())) })

        viewModel.state.test {

            // Skip initial state and loading state
            skipItems(2)

            val emission = awaitItem()

            // when list is empty, dayForecastItemList should be null if there wasn't any previous data
            Truth.assertThat(emission.isLoading).isFalse()
            Truth.assertThat(emission.isRefreshing).isFalse()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }

    @Test
    fun `emits dayForecastItemList when fetch is successful but list is empty and previous data was not empty`() =
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

                Truth.assertThat(emission.dayForecastItemList).isNotEmpty()
                Truth.assertThat(emission.isLoading).isFalse()
                Truth.assertThat(emission.isRefreshing).isFalse()
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

            Truth.assertThat(emission.isLoading).isFalse()
            Truth.assertThat(emission.isRefreshing).isFalse()
            Truth.assertThat(emission.dayForecastItemList).isEmpty()

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

            Truth.assertThat(emission.isLoading).isFalse()
            Truth.assertThat(emission.isRefreshing).isFalse()
            Truth.assertThat(emission.dayForecastItemList).isNotEmpty()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastListUseCase).invoke(any(), any())
    }
}