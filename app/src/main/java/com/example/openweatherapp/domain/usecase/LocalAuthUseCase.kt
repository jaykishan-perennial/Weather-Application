package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.utils.utility.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAuthUseCase @Inject constructor(private val repository: LocalAuthRepository) {

    suspend fun createUser(email: String, password: String): Flow<Response<Unit>> {
        return repository.createUser()
    }

}