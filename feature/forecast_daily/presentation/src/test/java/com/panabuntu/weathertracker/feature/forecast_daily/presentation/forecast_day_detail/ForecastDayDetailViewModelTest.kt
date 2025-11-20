package com.panabuntu.weathertracker.feature.forecast_daily.presentation.forecast_day_detail

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.testing.di.coreModuleTest
import com.panabuntu.weathertracker.core.testing.presentation.FakeNavArgsProvider
import com.panabuntu.weathertracker.feature.forecast_daily.model.DayForecastDetail
import com.panabuntu.weathertracker.feature.forecast_daily.presentation.mapper.toForecastDetailInfo
import com.panabuntu.weathertracker.feature.forecast_daily.usecase.GetDayForecastDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastDayDetailViewModelTest: KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(coreModuleTest)
    }

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ForecastDayDetailViewModel
    private val navArgsProvider: FakeNavArgsProvider by inject<FakeNavArgsProvider>()

    @Mock
    lateinit var getDayForecastDetailUseCase: GetDayForecastDetailUseCase
    @Mock
    lateinit var logger: AppLogger

    private val locationName = Const.DEFAULT_LOCATION_NAME
    private val lat = Const.DEFAULT_LAT
    private val lon = Const.DEFAULT_LON
    private val date = 123L

    private val dayForecastDetail=  DayForecastDetail(
        maxTemp = 23f,
        minTemp = 12f,
        description = "description",
        iconUrl = null,
        sunrise = LocalDateTime.now(),
        sunset = LocalDateTime.now(),
        humidity = 123,
        windSpeed = 123f,
        windDirectionDegrees = 0,
        timestamp = 123L,
        date = LocalDate.now(),
        rainProbability = 1f,
        uvIndex = 1f

    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)

        navArgsProvider.resultRoute = ForecastDayDetailRoute(
            locationName = locationName,
            lat = lat,
            lon = lon,
            date = date
        )

        viewModel = ForecastDayDetailViewModel(
            navArgsProvider = navArgsProvider,
            getDayForecastDetailUseCase = getDayForecastDetailUseCase,
            logger = logger
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `calls getDayForecastDetailUseCase in first subscription`() = runTest(testDispatcher) {

        // GIVEN → El usecase devuelve success vacío
        whenever(getDayForecastDetailUseCase(any(), any(), any())).thenReturn(
            flowOf(Result.Success(null))
        )

        // WHEN
        viewModel.state.test {
            awaitItem() // initial
            awaitItem() // loading
            // THEN
            verify(getDayForecastDetailUseCase).invoke(date = date, lat = lat, lon = lon)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `set loading state before calling getDayForecastDetailUseCase`() = runTest {

        val finalState = ForecastDayDetailState(
            locationName = locationName,
            isLoading = true,
            isRefreshing = false,
            errorMessage = null,
            forecastDetailInfo = null
        )

        whenever(getDayForecastDetailUseCase(any(), any(), any()))
            .thenReturn(flow { emit(Result.Error(NetworkError.SERVER_ERROR)) }
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
    fun `emits forecastDetailInfo when fetch is successful`() = runTest(testDispatcher) {

        whenever(getDayForecastDetailUseCase(any(), any(), any()))
            .thenReturn(flow { emit(Result.Success(dayForecastDetail)) }
        )

        val finalResult = dayForecastDetail.toForecastDetailInfo()

        viewModel.state.test {

            // Skip initial state and loading state
            skipItems(2)

            val emission = awaitItem()

            assertThat(emission.forecastDetailInfo).isNotNull()
            assertThat(emission.forecastDetailInfo!!.maxTemp).isEqualTo(finalResult!!.maxTemp)
            assertThat(emission.forecastDetailInfo.minTemp).isEqualTo(finalResult.minTemp)
            assertThat(emission.forecastDetailInfo.iconUrl).isEqualTo(finalResult.iconUrl)
            assertThat(emission.forecastDetailInfo.sunrise).isEqualTo(finalResult.sunrise)
            assertThat(emission.forecastDetailInfo.sunset).isEqualTo(finalResult.sunset)
            assertThat(emission.forecastDetailInfo.humidity).isEqualTo(finalResult.humidity)
            assertThat(emission.forecastDetailInfo.uvIndex).isEqualTo(finalResult.uvIndex)
            assertThat(emission.forecastDetailInfo.rainProbability).isEqualTo(finalResult.rainProbability)

            Truth.assertThat(emission.isLoading).isFalse()
            Truth.assertThat(emission.isRefreshing).isFalse()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastDetailUseCase).invoke(date = date, lat = lat, lon = lon)
    }


    @Test
    fun `emits forecastDetailInfo when fetch is successful but current data is null and previous data was not null`() =
        runTest(testDispatcher) {

            whenever(getDayForecastDetailUseCase(any(), any(), any()))
                .thenReturn(
                    flow {
                        emit(Result.Success(dayForecastDetail))
                        emit(Result.Success(null))
                    }
                )

            viewModel.state.test {

                // Skip initial state and loading state
                skipItems(2)

                val emission = awaitItem()

                Truth.assertThat(emission.forecastDetailInfo).isNotNull()
                Truth.assertThat(emission.isLoading).isFalse()
                Truth.assertThat(emission.isRefreshing).isFalse()
            }

            verify(getDayForecastDetailUseCase).invoke(date = date, lat = lat, lon = lon)
        }

    @Test
    fun `emits error when fetch fails and current forecastDetailInfo is null`() = runTest(testDispatcher) {

        whenever(getDayForecastDetailUseCase(any(), any(), any()))
            .thenReturn(flow { emit(Result.Error(NetworkError.SERVER_ERROR)) }
        )

        viewModel.state.test {

            skipItems(2)

            val emission = awaitItem()

            Truth.assertThat(emission.isLoading).isFalse()
            Truth.assertThat(emission.isRefreshing).isFalse()
            Truth.assertThat(emission.forecastDetailInfo).isNull()
            assertThat(emission.errorMessage).isNotNull()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastDetailUseCase).invoke(date = date, lat = lat, lon = lon)
    }

    @Test
    fun `emits error when fetch fails and current forecastDetailInfo is not null`() = runTest(testDispatcher) {
        whenever(getDayForecastDetailUseCase(any(), any(), any()))
            .thenReturn(
            flow {
                emit(Result.Success(dayForecastDetail))
                emit(Result.Error(NetworkError.SERVER_ERROR))
            }
        )

        viewModel.state.test {

            skipItems(2)

            val emission = awaitItem()

            Truth.assertThat(emission.isLoading).isFalse()
            Truth.assertThat(emission.isRefreshing).isFalse()
            Truth.assertThat(emission.forecastDetailInfo).isNotNull()

            ensureAllEventsConsumed()
        }

        verify(getDayForecastDetailUseCase).invoke(date = date, lat = lat, lon = lon)
    }
}