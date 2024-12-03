package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.data.source.local.entity.UserEntity
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.utils.utility.Response
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAuthUseCase @Inject constructor(private val repository: LocalAuthRepository) {

    suspend fun createUser(email: String, password: String): Flow<Response<Unit>> {

        val userEntity = UserEntity(
            email = email,
            password = password,
            firstName = "",
            createdAt = DateTime.now().toString(),
            updatedAt = DateTime.now().toString(),
        )

        return repository.createUser(userEntity)
    }

    suspend fun login(email: String, password: String): Flow<Response<Unit>> {
        return repository.loginUser(email, password)
    }

}