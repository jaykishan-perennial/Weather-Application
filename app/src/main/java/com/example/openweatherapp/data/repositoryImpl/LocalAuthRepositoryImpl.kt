package com.example.openweatherapp.data.repositoryImpl

import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.utils.utility.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAuthRepositoryImpl @Inject constructor(authDao: AuthDao) : LocalAuthRepository {
    override suspend fun createUser(): Flow<Response<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser() {
        TODO("Not yet implemented")
    }

    override suspend fun userExist(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        TODO("Not yet implemented")
    }

}