package com.example.openweatherapp.utils.enum

enum class ErrorCode(val code: Int) {
    INVALID_EMAIL(1),
    INVALID_PASSWORD(2),
    USER_ALREADY_EXISTS(3),
    USER_NOT_EXISTS(4),
    UNKNOWN_ERROR(5)
}
