package com.panabuntu.weathertracker.core.data.util

import com.panabuntu.weathertracker.core.domain.result.Error
import com.panabuntu.weathertracker.core.domain.result.NetworkError
import kotlinx.coroutines.flow.Flow
import com.panabuntu.weathertracker.core.domain.result.Result
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <DTO, MODEL> networkBoundResource(
    crossinline query: suspend () -> Flow<List<MODEL>>,
    crossinline fetch: suspend () -> Result<DTO, NetworkError>,
    crossinline saveFetchResult: suspend (DTO) -> Unit
): Flow<Result<List<MODEL>, Error>> = flow {

    val localData = query().first()

    if (localData.isEmpty().not()) {
        emit(Result.Success(localData))
    }

    when (val remoteData = fetch()) {
        is Result.Error -> emit(remoteData)
        is Result.Success -> {
            saveFetchResult(remoteData.data)
        }
    }

    emitAll(query().map { Result.Success(it) })
}