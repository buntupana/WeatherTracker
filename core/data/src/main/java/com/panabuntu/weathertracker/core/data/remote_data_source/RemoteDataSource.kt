package com.panabuntu.weathertracker.core.data.remote_data_source

import com.panabuntu.weathertracker.core.domain.util.AppLogger
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.channels.UnresolvedAddressException
import com.panabuntu.weathertracker.core.domain.result.Result
import kotlinx.coroutines.currentCoroutineContext

/**
 * Abstract Base Data source class with error handling
 */
abstract class RemoteDataSource : KoinComponent {

    val logger: AppLogger by inject()

    protected suspend inline fun <reified D> getResult(request: () -> HttpResponse): Result<D, NetworkError> {
        val response = try {
            request()
        } catch (e: UnresolvedAddressException) {
            logger.e(e)
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            logger.e(e)
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            logger.e(e)
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when (response.status.value) {

            in 200..299 -> {
                try {
                    Result.Success(response.body<D>())
                } catch (e: Exception) {
                    logger.e(e)
                    Result.Error(NetworkError.SERIALIZATION)
                }
            }

            401 -> {
                Result.Error(NetworkError.UNAUTHORIZED)
            }

            404 -> Result.Error(NetworkError.NOT_FOUND)

            409 -> Result.Error(NetworkError.CONFLICT)

            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)

            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)

            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)

            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)

            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}