package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.utils.AuthMockUtils
import com.example.openweatherapp.utils.MainDispatcherRule
import com.example.openweatherapp.utils.enum.ErrorCode
import com.example.openweatherapp.utils.utility.Response
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class LocalAuthUseCaseTest {
    private var mockRepository: LocalAuthRepository = mockk()
    private lateinit var useCase: LocalAuthUseCase

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = LocalAuthUseCase(mockRepository)
    }

    @Test
    fun `createUser invokes repository with correct UserEntity`() = runTest {
        val expectedResponse = flowOf(AuthMockUtils.successResponse)
        coEvery { mockRepository.createUser(any()) } returns expectedResponse
        val responseFlow = useCase.createUser(AuthMockUtils.email, AuthMockUtils.password)
        responseFlow.collect { response ->
            Assert.assertTrue(response is Response.Success)
        }
    }

    @Test
    fun `createUser handles repository error gracefully`() = runTest {
        val expectedResponse = flowOf(Response.Error(ErrorCode.USER_ALREADY_EXISTS.code))
        coEvery { mockRepository.createUser(any()) } returns expectedResponse
        val responseFlow = useCase.createUser(AuthMockUtils.email, AuthMockUtils.password)
        responseFlow.collect { response ->
            Assert.assertTrue(response is Response.Error)
            Assert.assertEquals(
                ErrorCode.USER_ALREADY_EXISTS.code, (response as Response.Error).errorCode
            )
        }
    }

    @Test
    fun `login invokes repository with correct inputs`() = runTest {
        val expectedResponse = flowOf(AuthMockUtils.successResponse)
        coEvery { mockRepository.loginUser(any(), any()) } returns expectedResponse
        val responseFlow = useCase.login(AuthMockUtils.email, AuthMockUtils.password)
        responseFlow.collect { response ->
            Assert.assertTrue(response is Response.Success)
        }
    }

    @Test
    fun `login handle repository error gracefully`() = runTest {
        val expectedResponse = flowOf(Response.Error(ErrorCode.UNKNOWN_ERROR.code))
        coEvery { mockRepository.loginUser(any(), any()) } returns expectedResponse
        val responseFlow = useCase.login(AuthMockUtils.email, AuthMockUtils.password)
        responseFlow.collect { response ->
            Assert.assertTrue(response is Response.Error)
            Assert.assertEquals(
                ErrorCode.UNKNOWN_ERROR.code, (response as Response.Error).errorCode
            )
        }
    }
}

