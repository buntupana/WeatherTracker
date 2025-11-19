package com.panabuntu.weathertracker.feature.forecast_daily.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.core.testing.database.FakeAppDataBase
import com.panabuntu.weathertracker.core.testing.di.coreModuleTest
import com.panabuntu.weathertracker.feature.forecast_daily.data.di.forecastDailyModuleTest
import com.panabuntu.weathertracker.feature.forecast_daily.data.dummy_data.DailyEntityDummy
import com.panabuntu.weathertracker.feature.forecast_daily.data.dummy_data.ForecastDailyDtoDummy
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toDayForecastEntity
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toDayForecastSimple
import com.panabuntu.weathertracker.feature.forecast_daily.data.remote_data_source.FakeForecastDailyRemoteDataSource
import com.panabuntu.weathertracker.feature.forecast_daily.repository.ForecastDailyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.time.LocalDate

class ForecastDailyRepositoryTest : KoinTest {

    private val repository by inject<ForecastDailyRepository>()
    private val database by inject<FakeAppDataBase>()
    private val remoteSource by inject<FakeForecastDailyRemoteDataSource>()
    private val urlProvider by inject<UrlProvider>()

    private val lat = 0.0
    private val lon = 0.0

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(coreModuleTest, forecastDailyModuleTest)
    }

    @Test
    fun `getDaily() emits cached data then fetched data and replace data with same date`() =
        runTest {

            // entity data
            val dailyEntityList = DailyEntityDummy.getDailyEntityList()
            database.dayForecastDao.upsertSimple(dailyEntityList)

            // dto data
            val remoteDaily = ForecastDailyDtoDummy.getSuccess()
            remoteSource.result = remoteDaily

            val firstResult = dailyEntityList
                .toDayForecastSimple({ urlProvider.createIconUrl(it) })
                .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

            // final result will be the data from network
            val finalResult = remoteDaily.data.daily
                .toDayForecastEntity(lat, lon)
                .toDayForecastSimple({ urlProvider.createIconUrl(it) })
                .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

            repository.getDayListForecast(lat, lon).test {

                // Local database data emitted
                val first = awaitItem()
                assertThat(first).isInstanceOf(Result.Success::class.java)
                assertThat((first as Result.Success).data)
                    .containsExactlyElementsIn(firstResult)

                // getting new data from network through database
                val second = awaitItem()
                assertThat(second).isInstanceOf(Result.Success::class.java)
                assertThat((second as Result.Success).data).containsExactlyElementsIn(finalResult)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getDaily() emits error when remote fails`() = runTest {
        val localDaily = DailyEntityDummy.getDailyEntityList()
        database.dayForecastDao.upsertSimple(localDaily)

        remoteSource.result = Result.Error(NetworkError.NO_INTERNET)

        // final result will be the data from network
        val finalResult = localDaily
            .toDayForecastSimple({ urlProvider.createIconUrl(it) })
            .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

        repository.getDayListForecast(0.0, 0.0).test {
            // Local database data emitted
            val first = awaitItem()
            assertThat(first).isInstanceOf(Result.Success::class.java)
            assertThat((first as Result.Success).data)
                .containsExactlyElementsIn(finalResult)

            // Network error emitted
            val error = awaitItem()
            assertThat(error).isInstanceOf(Result.Error::class.java)

            // Local database data emitted again
            val second = awaitItem()
            assertThat((second as Result.Success).data)
                .containsExactlyElementsIn(finalResult)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getDaily() removes previous data before emitting new data`() = runTest {
        // entity data with date previous than today
        val localDaily = DailyEntityDummy.getDailyEntityList(
            startFromLocalDate = LocalDate.now().minusMonths(1)
        )
        database.dayForecastDao.upsertSimple(localDaily)

        // dto data
        val remoteDaily = ForecastDailyDtoDummy.getSuccess()
        remoteSource.result = remoteDaily

        // final result will be the data from network
        val finalResult =
            remoteDaily.data.daily
                .toDayForecastEntity(lat, lon)
                .toDayForecastSimple({ urlProvider.createIconUrl(it) })
                .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

        repository.getDayListForecast(lat, lon).test {

            // getting new data from network through database
            val second = awaitItem()
            assertThat(second).isInstanceOf(Result.Success::class.java)
            assertThat((second as Result.Success).data).containsExactlyElementsIn(finalResult)

            cancelAndIgnoreRemainingEvents()
        }
    }
}