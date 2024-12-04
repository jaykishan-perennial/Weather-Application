package com.example.openweatherapp.ui.auth.fragments.login

import android.text.Editable
import com.example.openweatherapp.data.source.preference.LocalPreferences
import com.example.openweatherapp.domain.usecase.LocalAuthUseCase
import com.example.openweatherapp.ui.auth.utils.userValidation.EmailValidator
import com.example.openweatherapp.ui.auth.utils.userValidation.PasswordValidator
import com.example.openweatherapp.utils.AuthMockUtils
import com.example.openweatherapp.utils.MainDispatcherRule
import com.example.openweatherapp.utils.utility.Response
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.MockKException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class TestViewModel {
    private var emailValidator: EmailValidator = mockk()
    private var passwordValidator: PasswordValidator = mockk()

    private lateinit var viewModel: LoginViewModel
    private var localPreferences: LocalPreferences = mockk()
    private var localAuthUseCase: LocalAuthUseCase = mockk()
    private lateinit var responseState: MutableList<Response<Unit>?>

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setup() {

        MockKAnnotations.init(this, relaxed = true)
//        coEvery { localPreferences.getData(PrefsKeys.isLoggedIn, false) } returns flowOf(true)
        viewModel =
            LoginViewModel(emailValidator, passwordValidator, localAuthUseCase)

        responseState = mutableListOf()
        viewModel.loginResponse.observeForever {
            responseState.add(it)
        }
    }

    @Test
    fun `setEmail updates email LiveData`() {
        val mockEditable = mockk<Editable>()
        coEvery { mockEditable.toString() } returns AuthMockUtils.email
        viewModel.setEmail(mockEditable)
        assertEquals(AuthMockUtils.email, viewModel.email.value)
    }

    @Test
    fun `setPassword updates password LiveData`() {
        val mockPasswordEditable = mockk<Editable>()
        coEvery { mockPasswordEditable.toString() } returns AuthMockUtils.strongPassword
        viewModel.setPassword(mockPasswordEditable)
        assertEquals(AuthMockUtils.strongPassword, viewModel.password.value)
    }

    @Test
    fun `login triggers loading, validates inputs, and collects response`() {
        val mockEmailEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockEmailEditable.toString()).thenReturn(AuthMockUtils.email)
        viewModel.setEmail(mockEmailEditable)

        val mockPasswordEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockPasswordEditable.toString()).thenReturn(AuthMockUtils.strongPassword)
        viewModel.setPassword(mockPasswordEditable)

        viewModel.setEmail(mockEmailEditable)
        viewModel.setPassword(mockPasswordEditable)
        viewModel.setLoginAllowed()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        coEvery {
            localAuthUseCase.login(
                AuthMockUtils.email,
                AuthMockUtils.strongPassword
            )
        } returns flowOf(AuthMockUtils.successResponse)

        viewModel.login()

        coVerify { localAuthUseCase.login(AuthMockUtils.email, AuthMockUtils.strongPassword) }

        Assert.assertEquals(Response.Loading, responseState[0])
        Assert.assertEquals(Response.Success(Unit), responseState[1])
    }

    @Test
    fun `login triggers loading, validates inputs, and collects error response`() {
        val mockEmailEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockEmailEditable.toString()).thenReturn(AuthMockUtils.email)
        viewModel.setEmail(mockEmailEditable)

        val mockPasswordEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockPasswordEditable.toString()).thenReturn(AuthMockUtils.strongPassword)
        viewModel.setPassword(mockPasswordEditable)

        viewModel.setEmail(mockEmailEditable)
        viewModel.setPassword(mockPasswordEditable)
        viewModel.setLoginAllowed()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        coEvery {
            localAuthUseCase.login(
                AuthMockUtils.email,
                AuthMockUtils.strongPassword
            )
        } returns flowOf(AuthMockUtils.errorResponse)

        viewModel.login()

        coVerify { localAuthUseCase.login(AuthMockUtils.email, AuthMockUtils.strongPassword) }

        Assert.assertEquals(Response.Loading, responseState[0])
        Assert.assertEquals(AuthMockUtils.errorResponse, responseState[1])
    }

    @Test
    fun `login triggers loading, invalidates email inputs and returns`() {
        val mockEmailEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockEmailEditable.toString()).thenReturn(AuthMockUtils.invalidEmail)
        viewModel.setEmail(mockEmailEditable)

        val mockPasswordEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockPasswordEditable.toString()).thenReturn(AuthMockUtils.strongPassword)
        viewModel.setPassword(mockPasswordEditable)

        viewModel.setEmail(mockEmailEditable)
        viewModel.setPassword(mockPasswordEditable)
        viewModel.setLoginAllowed()

        coEvery { emailValidator.validate(AuthMockUtils.invalidEmail) } returns false

        viewModel.login()

        Assert.assertEquals(AuthMockUtils.invalidEmail, viewModel.email.value)
    }

    @Test
    fun `login triggers loading, invalidates password inputs and returns`() {
        val mockEmailEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockEmailEditable.toString()).thenReturn(AuthMockUtils.email)
        viewModel.setEmail(mockEmailEditable)

        val mockPasswordEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockPasswordEditable.toString()).thenReturn(AuthMockUtils.wrongPassword)
        viewModel.setPassword(mockPasswordEditable)

        viewModel.setEmail(mockEmailEditable)
        viewModel.setPassword(mockPasswordEditable)
        viewModel.setLoginAllowed()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.wrongPassword) } returns false

        viewModel.login()

        Assert.assertEquals(AuthMockUtils.wrongPassword, viewModel.password.value)
    }

    @Test
    fun `login triggers loading, validates inputs and returns exception`() {
        val errorResponse = Response.Error(5)

        val mockEmailEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockEmailEditable.toString()).thenReturn(AuthMockUtils.email)
        viewModel.setEmail(mockEmailEditable)

        val mockPasswordEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockPasswordEditable.toString()).thenReturn(AuthMockUtils.strongPassword)
        viewModel.setPassword(mockPasswordEditable)

        viewModel.setEmail(mockEmailEditable)
        viewModel.setPassword(mockPasswordEditable)
        viewModel.setLoginAllowed()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        coEvery {
            localAuthUseCase.login(
                AuthMockUtils.email,
                AuthMockUtils.strongPassword
            )
        }.throws(MockKException(AuthMockUtils.exceptionMessage))

        viewModel.login()

        coVerify { localAuthUseCase.login(AuthMockUtils.email, AuthMockUtils.strongPassword) }

        Assert.assertEquals(Response.Loading, responseState[0])
        Assert.assertEquals(errorResponse, responseState[1])
    }

    @Test
    fun `setLoginAllowed updates isLoginAllowed correctly`() = runTest {
        val mockEmailEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockEmailEditable.toString()).thenReturn(AuthMockUtils.email)
        viewModel.setEmail(mockEmailEditable)

        val mockPasswordEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockPasswordEditable.toString()).thenReturn(AuthMockUtils.strongPassword)
        viewModel.setPassword(mockPasswordEditable)

        viewModel.setEmail(mockEmailEditable)
        viewModel.setPassword(mockPasswordEditable)
        viewModel.setLoginAllowed()
    }

    @Test
    fun `clearStates should reset loginResponse to null`() = runTest {

        val mockEmailEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockEmailEditable.toString()).thenReturn(AuthMockUtils.email)
        viewModel.setEmail(mockEmailEditable)

        val mockPasswordEditable: Editable = Mockito.mock(Editable::class.java)
        `when`(mockPasswordEditable.toString()).thenReturn(AuthMockUtils.strongPassword)
        viewModel.setPassword(mockPasswordEditable)

        viewModel.setEmail(mockEmailEditable)
        viewModel.setPassword(mockPasswordEditable)
        viewModel.setLoginAllowed()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        coEvery {
            localAuthUseCase.login(AuthMockUtils.email, AuthMockUtils.strongPassword)
        } returns flowOf(AuthMockUtils.successResponse)

        viewModel.login()

        coVerify { localAuthUseCase.login(AuthMockUtils.email, AuthMockUtils.strongPassword) }


        viewModel.clearStates()

        assertThat(viewModel.loginResponse.value).isNull()
    }
}