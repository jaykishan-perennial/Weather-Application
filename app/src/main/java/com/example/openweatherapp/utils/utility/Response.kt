package com.example.openweatherapp.utils.utility

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
    object Loading : Response<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<Response<T>> {
    return this
        .map<T, Response<T>> {
            Response.Success(it)
        }
        .onStart { emit(Response.Loading) }
        .catch { emit(Response.Error(it.message.toString())) }
}