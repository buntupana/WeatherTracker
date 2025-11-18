package com.panabuntu.weathertracker.core.data

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.panabuntu.weathertracker.core.data.util.networkBoundResource
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import com.panabuntu.weathertracker.core.domain.result.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkBoundResourceTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    // Fake DTO y MODEL
    data class Dto(val items: List<String>)
    data class Model(val value: String)

//    @Test
//    fun `emits local data when not empty and then fetches and saves`() = runTest(testDispatcher) {
//
//        val local = listOf(Model("a"))
//
//        val query: suspend () -> Flow<List<Model>> = { flowOf(local) }
//        val fetch: suspend () -> Result<Dto, NetworkError> = {
//            Result.Success(Dto(emptyList()))
//        }
//        val save: suspend (Dto) -> Unit = {}
//
//        networkBoundResource(query, fetch, save).test {
//            val emissionBeforeFetch = awaitItem()
//            assertThat(emissionBeforeFetch).isInstanceOf(Result.Success::class.java)
//            assertThat((emissionBeforeFetch as Result.Success).data).containsExactlyElementsIn(local)
//
//            val emissionAfterFetch = awaitItem()
//            assertThat(emissionAfterFetch).isInstanceOf(Result.Success::class.java)
//            assertThat((emissionAfterFetch as Result.Success).data).containsExactlyElementsIn(local)
//
//            awaitComplete()
//        }
//    }
//
//    @Test
//    fun `does not emit local data when empty and then fetches and saves`() = runTest(testDispatcher) {
//        val local = emptyList<Model>()
//
//        var saveCalled = false
//
//        val query: suspend () -> Flow<List<Model>> = { flowOf(local) }
//        val fetch: suspend () -> Result<Dto, NetworkError> = {
//            Result.Success(Dto(listOf("x")))
//        }
//        val save: suspend (Dto) -> Unit = { saveCalled = true }
//
//        networkBoundResource(query, fetch, save).test {
//            val item = awaitItem()
//
//            assertThat(item).isInstanceOf(Result.Success::class.java)
//            assertThat(saveCalled).isTrue()
//
//            awaitComplete()
//        }
//    }
//
//    @Test
//    fun `emits error when fetch fails and then emits local data`() = runTest(testDispatcher) {
//        val local = emptyList<Model>()
//
//        val query: suspend () -> Flow<List<Model>> = { flowOf(local) }
//        val fetch: suspend () -> Result<Dto, NetworkError> = {
//            Result.Error(NetworkError.CONFLICT)
//        }
//        val save: suspend (Dto) -> Unit = {}
//
//        networkBoundResource(query, fetch, save).test {
//            val emission = awaitItem()
//
//            assertThat(emission).isInstanceOf(Result.Error::class.java)
//            assertThat((emission as Result.Error).error)
//                .isEqualTo(NetworkError.CONFLICT)
//
//            val emissionAfterFetch = awaitItem()
//            assertThat(emissionAfterFetch).isInstanceOf(Result.Success::class.java)
//
//            awaitComplete()
//        }
//    }
}