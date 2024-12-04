package com.example.openweatherapp.utils.utility

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val errorCode: Int) : Response<Nothing>()
    data object Loading : Response<Nothing>()
}