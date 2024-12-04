package com.example.openweatherapp.ui.auth.fragments.signup

import com.example.openweatherapp.domain.usecase.LocalAuthUseCase
import com.example.openweatherapp.ui.auth.utils.userValidation.EmailValidator
import com.example.openweatherapp.ui.auth.utils.userValidation.PasswordValidator
import com.example.openweatherapp.utils.AuthMockUtils
import com.example.openweatherapp.utils.MainDispatcherRule
import com.example.openweatherapp.utils.utility.Response
import io.mockk.MockKAnnotations
import io.mockk.MockKException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel
    private var emailValidator: EmailValidator = mockk()
    private var passwordValidator: PasswordValidator = mockk()
    private var localAuthUseCase: LocalAuthUseCase = mockk()
    private lateinit var responseState: MutableList<Response<Unit>?>

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel =
            SignUpViewModel(emailValidator, passwordValidator, localAuthUseCase)
        responseState = mutableListOf()
        viewModel.signUpState.observeForever {
            responseState.add(it)
        }
    }

    @Test
    fun `setEmail updates email LiveData`() {
        val mockEmail: CharSequence = mockk<CharSequence>()
        coEvery { mockEmail.toString() } returns AuthMockUtils.email
        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        viewModel.setEmail(mockEmail)
        assertEquals(AuthMockUtils.email, viewModel.email.value)
        assertEquals(true, viewModel.isValidEmail.get())
    }

    @Test
    fun `setPassword updates password LiveData`() {
        val mockPassword = mockk<CharSequence>()
        coEvery { mockPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        viewModel.setPassword(mockPassword)
        assertEquals(AuthMockUtils.strongPassword, viewModel.password.value)
        assertEquals(true, viewModel.isValidPassword.get())
    }

    @Test
    fun `setConfirmPassword updates confirm password LiveData`() {
        val mockConfirmPassword = mockk<CharSequence>()
        coEvery { mockConfirmPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(viewModel.password.value) } returns true
        viewModel.setConfirmPassword(mockConfirmPassword)
        assertEquals(AuthMockUtils.strongPassword, viewModel.confirmPassword.value)
    }

    @Test
    fun `signup triggers loading, validates inputs, and collects response`() {
        val mockEmail: CharSequence = mockk<CharSequence>()
        coEvery { mockEmail.toString() } returns AuthMockUtils.email
        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        viewModel.setEmail(mockEmail)

        val mockPassword = mockk<CharSequence>()
        coEvery { mockPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        viewModel.setPassword(mockPassword)

        val mockConfirmPassword = mockk<CharSequence>()
        coEvery { mockConfirmPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(viewModel.password.value) } returns true
        viewModel.setConfirmPassword(mockConfirmPassword)

        viewModel.setEmail(mockEmail)
        viewModel.setPassword(mockPassword)
        viewModel.setConfirmPassword(mockConfirmPassword)
        viewModel.validateBothNewPasswordsSame()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        coEvery {
            localAuthUseCase.createUser(AuthMockUtils.email, AuthMockUtils.strongPassword)
        } returns flowOf(AuthMockUtils.successResponse)

        viewModel.signUp()

        coVerify { localAuthUseCase.createUser(AuthMockUtils.email, AuthMockUtils.strongPassword) }

        Assert.assertEquals(Response.Loading, responseState[0])
        Assert.assertEquals(Response.Success(Unit), responseState[1])
    }

    @Test
    fun `signup triggers loading, validates inputs, and collects error response`() {
        val mockEmail: CharSequence = mockk<CharSequence>()
        coEvery { mockEmail.toString() } returns AuthMockUtils.email
        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        viewModel.setEmail(mockEmail)

        val mockPassword = mockk<CharSequence>()
        coEvery { mockPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        viewModel.setPassword(mockPassword)


        val mockConfirmPassword = mockk<CharSequence>()
        coEvery { mockConfirmPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(viewModel.password.value) } returns true
        viewModel.setConfirmPassword(mockConfirmPassword)

        viewModel.setEmail(mockEmail)
        viewModel.setPassword(mockPassword)
        viewModel.setConfirmPassword(mockConfirmPassword)
        viewModel.validateBothNewPasswordsSame()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        coEvery {
            localAuthUseCase.createUser(AuthMockUtils.email, AuthMockUtils.strongPassword)
        } returns flowOf(AuthMockUtils.errorResponse)

        viewModel.signUp()

        coVerify { localAuthUseCase.createUser(AuthMockUtils.email, AuthMockUtils.strongPassword) }

        Assert.assertEquals(Response.Loading, responseState[0])
        Assert.assertEquals(AuthMockUtils.errorResponse, responseState[1])
    }

    @Test
    fun `signup triggers loading, invalidates email inputs and returns`() {
        val mockEmail: CharSequence = mockk<CharSequence>()
        coEvery { mockEmail.toString() } returns AuthMockUtils.invalidEmail
        coEvery { emailValidator.validate(AuthMockUtils.invalidEmail) } returns false
        viewModel.setEmail(mockEmail)

        val mockPassword = mockk<CharSequence>()
        coEvery { mockPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        viewModel.setPassword(mockPassword)

        val mockConfirmPassword = mockk<CharSequence>()
        coEvery { mockConfirmPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(viewModel.password.value) } returns true
        viewModel.setConfirmPassword(mockConfirmPassword)

        viewModel.setEmail(mockEmail)
        viewModel.setPassword(mockPassword)
        viewModel.setConfirmPassword(mockConfirmPassword)
        viewModel.validateBothNewPasswordsSame()

        coEvery { emailValidator.validate(AuthMockUtils.invalidEmail) } returns false

        viewModel.signUp()

        Assert.assertEquals(AuthMockUtils.invalidEmail, viewModel.email.value)
    }

    @Test
    fun `signup triggers loading, invalidates password inputs and returns`() {
        val mockEmail: CharSequence = mockk<CharSequence>()
        coEvery { mockEmail.toString() } returns AuthMockUtils.email
        coEvery { emailValidator.validate(AuthMockUtils.email) } returns false
        viewModel.setEmail(mockEmail)

        val mockPassword = mockk<CharSequence>()
        coEvery { mockPassword.toString() } returns AuthMockUtils.wrongPassword
        coEvery { passwordValidator.validate(AuthMockUtils.wrongPassword) } returns true
        viewModel.setPassword(mockPassword)

        val mockConfirmPassword = mockk<CharSequence>()
        coEvery { mockConfirmPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(viewModel.password.value) } returns true
        viewModel.setConfirmPassword(mockConfirmPassword)

        viewModel.setEmail(mockEmail)
        viewModel.setPassword(mockPassword)
        viewModel.setConfirmPassword(mockConfirmPassword)
        viewModel.validateBothNewPasswordsSame()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.wrongPassword) } returns false

        viewModel.signUp()

        Assert.assertEquals(AuthMockUtils.wrongPassword, viewModel.password.value)
    }

    @Test
    fun `signup triggers loading, validates inputs and returns exception`() {
        val errorResponse = Response.Error(5)

        val mockEmail: CharSequence = mockk<CharSequence>()
        coEvery { mockEmail.toString() } returns AuthMockUtils.email
        coEvery { emailValidator.validate(AuthMockUtils.email) } returns false
        viewModel.setEmail(mockEmail)

        val mockPassword = mockk<CharSequence>()
        coEvery { mockPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        viewModel.setPassword(mockPassword)

        val mockConfirmPassword = mockk<CharSequence>()
        coEvery { mockConfirmPassword.toString() } returns AuthMockUtils.strongPassword
        coEvery { passwordValidator.validate(viewModel.password.value) } returns true
        viewModel.setConfirmPassword(mockConfirmPassword)

        viewModel.setEmail(mockEmail)
        viewModel.setPassword(mockPassword)
        viewModel.setConfirmPassword(mockConfirmPassword)
        viewModel.validateBothNewPasswordsSame()

        coEvery { emailValidator.validate(AuthMockUtils.email) } returns true
        coEvery { passwordValidator.validate(AuthMockUtils.strongPassword) } returns true
        coEvery {
            localAuthUseCase.createUser(AuthMockUtils.email, AuthMockUtils.strongPassword)
        }.throws(MockKException(AuthMockUtils.exceptionMessage))

        viewModel.signUp()

        coVerify { localAuthUseCase.createUser(AuthMockUtils.email, AuthMockUtils.strongPassword) }

        Assert.assertEquals(Response.Loading, responseState[0])
        Assert.assertEquals(errorResponse, responseState[1])
    }

}