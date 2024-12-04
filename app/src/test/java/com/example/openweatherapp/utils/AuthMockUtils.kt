package com.example.openweatherapp.utils

import com.example.openweatherapp.data.source.local.entity.UserEntity
import com.example.openweatherapp.utils.enum.ErrorCode
import com.example.openweatherapp.utils.utility.Response

object AuthMockUtils {

    val email = "test@test.com"
    val inputEmail = "test1@example.com"
    val password = "password"
    val strongPassword = "StrongPass1!"
    val wrongPassword = "wrong_password"
    val invalidEmail = "test1"

    val exceptionMessage = "Unknown error occurred"
    val exceptionCode = ErrorCode.UNKNOWN_ERROR.code
    val errorResponse = Response.Error(5)
    val successResponse = Response.Success(Unit)

    val userEntity = UserEntity(
        email = "test@test.com",
        password = "password",
        firstName = "Test",
        createdAt = "2024-12-01",
        updatedAt = "2024-12-01"
    )
}