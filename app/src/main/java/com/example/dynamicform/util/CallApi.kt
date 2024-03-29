package com.example.dynamicform.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

abstract class CallApi {
    suspend fun <T> safeCallRemote(callApi: suspend () -> Response<T>) = flow {
        emit(ApiDataState.Loading)
        val response = callApi()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiDataState.Success(it))
            } ?: kotlin.run {
                emit(
                    ApiDataState.Error(
                        IOException(
                            response.errorBody()?.string() ?: "Unknown error"
                        )
                    )
                )
                return@flow
            }
        } else {
            emit(ApiDataState.Error(Throwable(response.errorBody()?.string())))
            return@flow
        }

    }.catch { e ->
        emit(ApiDataState.Error(Exception(e)))
    }.flowOn(Dispatchers.IO)
}