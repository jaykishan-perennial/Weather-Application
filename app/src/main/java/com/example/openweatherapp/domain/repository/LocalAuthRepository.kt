package com.example.openweatherapp.domain.repository

import com.example.openweatherapp.utils.utility.Response
import kotlinx.coroutines.flow.Flow

interface LocalAuthRepository {

    suspend fun createUser(): Flow<Response<Unit>>

    suspend fun loginUser()

    suspend fun userExist(): Boolean

    suspend fun logoutUser()
}