package com.example.openweatherapp.ui.auth.fragments.signup

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherapp.domain.usecase.LocalAuthUseCase
import com.example.openweatherapp.ui.auth.utils.userValidation.EmailValidator
import com.example.openweatherapp.ui.auth.utils.userValidation.PasswordValidator
import com.example.openweatherapp.utils.enum.ErrorCode
import com.example.openweatherapp.utils.utility.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val emailValidator: EmailValidator, private val passwordValidator: PasswordValidator,
    private val localAuthUseCase: LocalAuthUseCase,
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    fun setEmail(value: CharSequence?) {
        _email.value = value.toString()
        validateEmail()
    }

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    fun setPassword(value: CharSequence?) {
        _password.value = value.toString()
        validatePassword()
    }

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    fun setConfirmPassword(value: CharSequence?) {
        _confirmPassword.value = value.toString()
        validatePassword()
    }

    val isValidEmail = ObservableBoolean(true)

    val isValidPassword = ObservableBoolean(true)

    val isBothPasswordsSame = ObservableBoolean(true)

    private val _signUpResponse = MutableLiveData<Response<Unit>?>()
    val signUpState: LiveData<Response<Unit>?> get() = _signUpResponse

    private fun setSignUpResponse(value: Response<Unit>) {
        _signUpResponse.postValue(value)
    }

    fun signUp() {
        viewModelScope.launch {
            setSignUpResponse(Response.Loading)

            if (!emailValidator.validate(_email.value)) {
                return@launch
            }
            if (!passwordValidator.validate(_password.value)) {
                return@launch
            }

            try {
//                delay(1000)
                val response = localAuthUseCase.createUser(_email.value!!, _password.value!!)
                response.collect { setSignUpResponse(it) }
            } catch (e: Exception) {
                setSignUpResponse(Response.Error(ErrorCode.UNKNOWN_ERROR.code))
            }
        }
    }


   private fun validateEmail() {
        isValidEmail.set(
            emailValidator.validate(_email.value)
        )
    }

    private fun validatePassword() {
        isValidPassword.set(
            passwordValidator.validate(_password.value)
        )
    }

    fun validateBothNewPasswordsSame() {
        isBothPasswordsSame.set(
            _password.value.equals(_confirmPassword.value)
        )
    }
}