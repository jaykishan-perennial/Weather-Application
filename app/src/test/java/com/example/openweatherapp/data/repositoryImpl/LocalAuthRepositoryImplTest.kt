package com.example.openweatherapp.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.preference.LocalPreferences
import com.example.openweatherapp.data.source.preference.PrefsKeys
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.domain.security.SecurePreferences
import com.example.openweatherapp.domain.security.SecurityUtil
import com.example.openweatherapp.utils.AuthMockUtils
import com.example.openweatherapp.utils.enum.ErrorCode
import com.example.openweatherapp.utils.utility.Response
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class LocalAuthRepositoryImplTest {

    private lateinit var authDao: AuthDao
    private lateinit var context: Context

    @Mock
    private lateinit var dataStore: DataStore<Preferences>

    private  var securityUtil: SecurityUtil = mockk()
    private lateinit var localPreferences: LocalPreferences
    private lateinit var securePreferences: SecurePreferences
    private lateinit var localAuthRepository: LocalAuthRepository

    @Before
    fun setup() {
        authDao = Mockito.mock(AuthDao::class.java)
        context = Mockito.mock(Context::class.java)
//        securityUtil = Mockito.mock(SecurityUtil::class.java)
        localPreferences = LocalPreferences(dataStore)
        val gson = Gson()
        securePreferences = SecurePreferences(localPreferences, securityUtil, gson)
        localAuthRepository = LocalAuthRepositoryImpl(authDao, securePreferences)
    }

    @Test
    fun `create user successfully`() = runTest {
        `when`(authDao.checkUserExist(AuthMockUtils.email)).thenReturn(false)
        coEvery { securePreferences.setData(PrefsKeys.isLoggedIn, true) } returns Unit
        val result = localAuthRepository.createUser(AuthMockUtils.userEntity).toList()

        Assert.assertEquals(Response.Loading, result[0])
        Assert.assertEquals(Response.Success(Unit), result[1])
    }

    @Test
    fun `create user fails when user already exists`() = runTest {
        `when`(authDao.checkUserExist(AuthMockUtils.email)).thenReturn(true)

        val result = localAuthRepository.createUser(AuthMockUtils.userEntity).toList()

        Assert.assertEquals(Response.Loading, result[0])
        Assert.assertEquals(Response.Error(ErrorCode.USER_ALREADY_EXISTS.code), result[1])
    }

    @Test
    fun `login user successfully`() = runTest {
        `when`(authDao.getUserByEmail(AuthMockUtils.email)).thenReturn(AuthMockUtils.userEntity)

        val result =
            localAuthRepository.loginUser(AuthMockUtils.email, AuthMockUtils.password).toList()

        Assert.assertEquals(Response.Loading, result[0])
        Assert.assertEquals(Response.Success(Unit), result[1])
    }

    @Test
    fun `login user fails when email is not found`() = runTest {
        `when`(authDao.getUserByEmail(AuthMockUtils.email)).thenReturn(null)

        val result =
            localAuthRepository.loginUser(AuthMockUtils.inputEmail, AuthMockUtils.password).toList()
        Assert.assertEquals(Response.Loading, result[0])
        Assert.assertEquals(Response.Error(ErrorCode.USER_NOT_EXISTS.code), result[1])
    }

    @Test
    fun `login user fails when password is incorrect`() = runTest {
        `when`(authDao.getUserByEmail(AuthMockUtils.email)).thenReturn(AuthMockUtils.userEntity)

        val result =
            localAuthRepository.loginUser(AuthMockUtils.email, AuthMockUtils.wrongPassword).toList()

        Assert.assertEquals(Response.Loading, result[0])
        Assert.assertEquals(Response.Error(ErrorCode.INVALID_PASSWORD.code), result[1])
    }

    @Test
    fun `should throw Exception when login user called`() = runTest {
        val mockitoException = MockitoException(AuthMockUtils.exceptionMessage)

        `when`(authDao.getUserByEmail(AuthMockUtils.email)).thenThrow(mockitoException)

        val result =
            localAuthRepository.loginUser(AuthMockUtils.email, AuthMockUtils.wrongPassword).toList()

        Assert.assertEquals(Response.Loading, result[0])
        Assert.assertEquals(AuthMockUtils.exceptionCode, (result[1] as Response.Error).errorCode)
    }

    @Test
    fun `should throw Exception when create user called`() = runTest {

        val mockitoException = MockitoException(AuthMockUtils.exceptionMessage)

        `when`(authDao.checkUserExist(AuthMockUtils.email)).thenThrow(mockitoException)

        val result = localAuthRepository.createUser(AuthMockUtils.userEntity).toList()

        Assert.assertEquals(Response.Loading, result[0])
        Assert.assertEquals(AuthMockUtils.exceptionCode, (result[1] as Response.Error).errorCode)
    }
}
