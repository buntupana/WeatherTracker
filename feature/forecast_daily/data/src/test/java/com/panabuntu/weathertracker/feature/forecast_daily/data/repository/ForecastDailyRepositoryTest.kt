package com.panabuntu.weathertracker.feature.forecast_daily.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.panabuntu.weathertracker.core.domain.Const
import com.panabuntu.weathertracker.core.domain.provider.UrlProvider
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import com.panabuntu.weathertracker.core.domain.util.toUTCStartOfDayTimestamp
import com.panabuntu.weathertracker.core.testing.database.FakeAppDataBase
import com.panabuntu.weathertracker.core.testing.di.coreModuleTest
import com.panabuntu.weathertracker.feature.forecast_daily.data.di.forecastDailyModuleTest
import com.panabuntu.weathertracker.feature.forecast_daily.data.dummy_data.DailyEntityDummy
import com.panabuntu.weathertracker.feature.forecast_daily.data.dummy_data.ForecastDailyDtoDummy
import com.panabuntu.weathertracker.feature.forecast_daily.data.mapper.toDayForecastDetail
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
    fun `getDayForecastList() emits cached data then fetched data and replace data with same date`() =
        runTest {

            // entity data
            val dailyEntityList = DailyEntityDummy.getDailyEntityList()
            database.dayForecastDao.upsert(dailyEntityList)

            // dto data
            val remoteDaily = ForecastDailyDtoDummy.getSuccess()
            remoteSource.result = remoteDaily

            val firstResult = dailyEntityList
                .toDayForecastSimple { urlProvider.createIconUrl(it) }
                .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

            // final result will be the data from network
            val finalResult = remoteDaily.data.daily
                .toDayForecastEntity(lat, lon)
                .toDayForecastSimple { urlProvider.createIconUrl(it) }
                .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

            repository.getDayForecastList(lat, lon).test {

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
    fun `getDayForecastList() emits error when remote fails`() = runTest {
        val localDaily = DailyEntityDummy.getDailyEntityList()
        database.dayForecastDao.upsert(localDaily)

        remoteSource.result = Result.Error(NetworkError.NO_INTERNET)

        // final result will be the data from network
        val finalResult = localDaily
            .toDayForecastSimple { urlProvider.createIconUrl(it) }
            .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

        repository.getDayForecastList(0.0, 0.0).test {
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
    fun `getDayForecastList() removes previous data before emitting new data`() = runTest {
        // entity data with date previous than today
        val localDaily = DailyEntityDummy.getDailyEntityList(
            startFromLocalDate = LocalDate.now().minusMonths(1)
        )
        database.dayForecastDao.upsert(localDaily)

        // dto data
        val remoteDaily = ForecastDailyDtoDummy.getSuccess()
        remoteSource.result = remoteDaily

        // final result will be the data from network
        val finalResult =
            remoteDaily.data.daily
                .toDayForecastEntity(lat, lon)
                .toDayForecastSimple { urlProvider.createIconUrl(it) }
                .take(Const.DEFAULT_NUMBER_DAILY_ITEMS)

        repository.getDayForecastList(lat, lon).test {

            // getting new data from network through database
            val second = awaitItem()
            assertThat(second).isInstanceOf(Result.Success::class.java)
            assertThat((second as Result.Success).data).containsExactlyElementsIn(finalResult)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getDayForecast() emits cached data then fetched data and replace data with same date`() =
        runTest {

            val currentDate = LocalDate.now()

            // entity data
            val dailyEntityList =
                DailyEntityDummy.getDailyEntityList(startFromLocalDate = currentDate)
            database.dayForecastDao.upsert(dailyEntityList)

            // dto data
            val remoteDaily = ForecastDailyDtoDummy.getSuccess(startFromLocalDate = currentDate)
            remoteSource.result = remoteDaily

            val firstResult = dailyEntityList.first()
                .toDayForecastDetail { urlProvider.createIconUrl(it) }

            // final result will be the data from network
            val finalResult = remoteDaily.data.daily
                .toDayForecastEntity(lat, lon).first()
                .toDayForecastDetail() { urlProvider.createIconUrl(it) }

            repository.getDayForecast(
                date = currentDate.toUTCStartOfDayTimestamp(),
                lat = lat,
                lon = lon
            ).test {

                // Local database data emitted
                val first = awaitItem()
                assertThat(first).isInstanceOf(Result.Success::class.java)
                assertThat((first as Result.Success).data).isNotNull()
                assertThat(first.data).isEqualTo(firstResult)

                // getting new data from network through database
                val second = awaitItem()
                assertThat(second).isInstanceOf(Result.Success::class.java)
                assertThat((second as Result.Success).data).isNotNull()
                assertThat(second.data).isEqualTo(finalResult)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getDayForecast() emits error when remote fails`() = runTest {

        val currentDate = LocalDate.now()

        val localDaily = DailyEntityDummy.getDailyEntityList(startFromLocalDate = currentDate)
        database.dayForecastDao.upsert(localDaily)

        remoteSource.result = Result.Error(NetworkError.NO_INTERNET)

        // final result will be the data from network
        val finalResult = localDaily.first()
            .toDayForecastDetail { urlProvider.createIconUrl(it) }


        repository.getDayForecast(
            date = currentDate.toUTCStartOfDayTimestamp(),
            lat = 0.0,
            lon = 0.0
        ).test {
            // Local database data emitted
            val first = awaitItem()
            assertThat(first).isInstanceOf(Result.Success::class.java)
            assertThat((first as Result.Success).data).isEqualTo(finalResult)

            // Network error emitted
            val error = awaitItem()
            assertThat(error).isInstanceOf(Result.Error::class.java)

            // Local database data emitted again
            val second = awaitItem()
            assertThat((second as Result.Success).data).isEqualTo(finalResult)

            cancelAndIgnoreRemainingEvents()
        }
    }
}