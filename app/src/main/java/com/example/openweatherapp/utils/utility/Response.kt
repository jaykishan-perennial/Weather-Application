package com.example.openweatherapp.utils.utility

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Response<T>(
    val data: T? = null, val message: String? = null, val isLoading: Boolean? = null,
) {
    class Success<T>(data: T?) : Response<T>(data = data)
    class Error<T>(message: String?) : Response<T>(message = message)
    class Loading<T>(isLoading: Boolean) : Response<T>(isLoading = isLoading)
}

fun <T> Flow<T>.asResult(): Flow<Response<T>> {
    return this
        .map<T, Response<T>> {
            Response.Success(it)
        }
        .onStart { emit(Response.Loading(isLoading = false)) }
        .catch { emit(Response.Error(it.message)) }
}