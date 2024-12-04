package com.example.openweatherapp.data.repositoryImpl

import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.local.entity.UserEntity
import com.example.openweatherapp.data.source.preference.LocalPreferences
import com.example.openweatherapp.data.source.preference.PrefsKeys
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.domain.security.SecurePreferences
import com.example.openweatherapp.utils.enum.ErrorCode
import com.example.openweatherapp.utils.utility.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalAuthRepositoryImpl @Inject constructor(
    private val authDao: AuthDao,
    private val securePreferences: SecurePreferences
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
                Response.Error(ErrorCode.USER_ALREADY_EXISTS.code)
            } else {
                authDao.createUser(userEntity)
                //securePreferences.setData(PrefsKeys.isLoggedIn, true)
                Response.Success(Unit)
            }
        } catch (exception: Exception) {
            Response.Error(ErrorCode.UNKNOWN_ERROR.code)
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
                Response.Error(ErrorCode.USER_NOT_EXISTS.code)
            } else if (user.password != password) {
                Response.Error(ErrorCode.INVALID_PASSWORD.code)
            } else {
                //securePreferences.setData(PrefsKeys.isLoggedIn, true)
                Response.Success(Unit)
            }
        } catch (exception: Exception) {
            Response.Error(ErrorCode.UNKNOWN_ERROR.code)
        }
    }
}