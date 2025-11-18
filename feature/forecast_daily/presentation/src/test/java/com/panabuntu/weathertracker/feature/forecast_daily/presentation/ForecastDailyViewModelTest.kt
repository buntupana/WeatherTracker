package com.panabuntu.weathertracker.feature.forecast_daily.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.feature.forecast_daily.repository.model.Daily
import com.panabuntu.weathertracker.feature.forecast_daily.repository.usecase.GetDailyForecastUseCase
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
    lateinit var getDailyForecastUseCase: GetDailyForecastUseCase

    private lateinit var viewModel: ForecastDailyViewModel

    private val dailyList: List<Daily>
        get() {
            return (0..6).map { index ->
                Daily(
                    date = LocalDate.now().plusDays(index.toLong()),
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
        viewModel = ForecastDailyViewModel(logger, getDailyForecastUseCase)
    }

    @Test
    fun `calls GetDailyForecast on first state subscription`() = runTest {

        whenever(getDailyForecastUseCase(any(), any())).thenReturn(
            flow { emit(Result.Error(NetworkError.SERVER_ERROR)) }
        )

        viewModel.state.test {

            val emission = awaitItem()

            assertThat(emission.isLoading).isFalse()
            assertThat(emission.isRefreshing).isFalse()
            assertThat(emission.dailyList).isEmpty()

            cancelAndConsumeRemainingEvents()
        }

        verify(getDailyForecastUseCase).invoke(any(), any())
    }

    @Test
    fun `set loading state before calling GetDailyForecast`() = runTest {

        val finalState = ForecastDailyState(
            isLoading = true,
            isRefreshing = false,
            locationName = Const.DEFAULT_LOCATION_NAME
        )

        whenever(getDailyForecastUseCase(any(), any())).thenReturn(
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

        whenever(getDailyForecastUseCase(any(), any())).thenReturn(
            flow { emit(Result.Success(dailyList)) }
        )

        viewModel.state.test {

            // Skip initial state and loading state
            skipItems(2)

            val emission = awaitItem()

            assertThat(emission.dailyList.size).isEqualTo(dailyList.size)

            assertThat(emission.isLoading).isFalse()
            assertThat(emission.isRefreshing).isFalse()

            ensureAllEventsConsumed()
        }

        verify(getDailyForecastUseCase).invoke(any(), any())
    }

    @Test
    fun `emits dailyList when fetch is successful but list is empty`() = runTest(testDispatcher) {

        whenever(getDailyForecastUseCase(any(), any()))
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

        verify(getDailyForecastUseCase).invoke(any(), any())
    }

    @Test
    fun `emits dailyList when fetch is successful but list is empty and previous data was not null`() =
        runTest(testDispatcher) {

            whenever(getDailyForecastUseCase(any(), any()))
                .thenReturn(
                    flow {
                        emit(Result.Success(dailyList))
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

            verify(getDailyForecastUseCase).invoke(any(), any())
        }

    @Test
    fun `emits error when fetch fails and current list is empty`() = runTest(testDispatcher) {

        whenever(getDailyForecastUseCase(any(), any())).thenReturn(
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

        verify(getDailyForecastUseCase).invoke(any(), any())
    }

    @Test
    fun `emits error when fetch fails and current list is not empty`() = runTest(testDispatcher) {
        whenever(getDailyForecastUseCase(any(), any())).thenReturn(
            flow {
                emit(Result.Success(dailyList))
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

        verify(getDailyForecastUseCase).invoke(any(), any())
    }
}