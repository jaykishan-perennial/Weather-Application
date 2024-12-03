package com.example.openweatherapp.data.repositoryImpl

import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.local.entity.UserEntity
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.utils.utility.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import com.example.openweatherapp.R
import com.example.openweatherapp.domain.repository.ResourceProviderRepository

@Singleton
class LocalAuthRepositoryImpl @Inject constructor(
    private val authDao: AuthDao, private val resource: ResourceProviderRepository
) : LocalAuthRepository {
    override suspend fun createUser(userEntity: UserEntity): Flow<Response<Unit>> {
        return flow {
            emit(Response.Loading)
            emit(createUserInternal(userEntity))
        }
    }

    private suspend fun createUserInternal(userEntity: UserEntity): Response<Unit> {
        return try {
            val userAlreadyExist = authDao.checkUserExist(userEntity.email)
            if (userAlreadyExist) {
                Response.Error(resource.getString(R.string.txt_user_with_this_email_already_exists))
            } else {
                authDao.createUser(userEntity)
                Response.Success(Unit)
            }
        } catch (exception: Exception) {
            Response.Error(
                exception.message ?: resource.getString(R.string.txt_unknown_error_occurred)
            )
        }
    }

    override suspend fun loginUser(email: String, password: String): Flow<Response<Unit>> {
        return flow {
            emit(Response.Loading)
            emit(loginUserInternal(email, password))
        }
    }

    private suspend fun loginUserInternal(email: String, password: String): Response<Unit> {
        return try {
            val user = authDao.getUserByEmail(email)
            if (user == null) {
                Response.Error(resource.getString(R.string.txt_user_with_this_email_does_not_exist))
            } else if (user.password != password) {
                Response.Error(resource.getString(R.string.txt_user_password_is_incorrect))
            } else {
                Response.Success(Unit)
            }
        } catch (exception: Exception) {
            Response.Error(
                exception.message ?: resource.getString(R.string.txt_unknown_error_occurred)
            )
        }
    }
}