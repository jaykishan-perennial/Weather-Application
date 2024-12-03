package com.example.openweatherapp.domain.repository

import com.example.openweatherapp.data.source.local.entity.UserEntity
import com.example.openweatherapp.utils.utility.Response
import kotlinx.coroutines.flow.Flow

interface LocalAuthRepository {

    suspend fun createUser(userEntity: UserEntity): Flow<Response<Unit>>

    suspend fun loginUser(email: String, password: String): Flow<Response<Unit>>

}