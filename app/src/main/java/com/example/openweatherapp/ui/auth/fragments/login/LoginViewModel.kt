package com.example.openweatherapp.ui.auth.fragments.login

import android.text.Editable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherapp.domain.usecase.LocalAuthUseCase
import com.example.openweatherapp.ui.auth.utils.userValidation.EmailValidator
import com.example.openweatherapp.ui.auth.utils.userValidation.PasswordValidator
import com.example.openweatherapp.utils.utility.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidator: EmailValidator, private val passwordValidator: PasswordValidator,
    private val localAuthUseCase: LocalAuthUseCase
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    fun setEmail(value: Editable) {
        _email.postValue(value.toString())
    }

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    fun setPassword(value: Editable) {
        _password.postValue(value.toString())
    }

    val isLoginAllowed = ObservableBoolean(false)

    private val _loginResponse = MutableLiveData<Response<Unit>?>()
    val loginResponse: LiveData<Response<Unit>?> get() = _loginResponse

    private fun setLoginState(value: Response<Unit>) {
        _loginResponse.postValue(value)
    }

    fun login() {
        viewModelScope.launch {
            setLoginState(Response.Loading)

            if (!emailValidator.validate(_email.value)) {
                return@launch
            }
            if (!passwordValidator.validate(_password.value)) {
                return@launch
            }

            try {
                delay(1000)
                val response = localAuthUseCase.login(_email.value!!, _password.value!!)
                response.collect { setLoginState(it) }
            } catch (e: Exception) {
                setLoginState(Response.Error(e.message.toString()))
            }
        }
    }

    fun setLoginAllowed() {
        isLoginAllowed.set(
            _email.value.isNullOrEmpty().not() and _password.value.isNullOrEmpty().not()
        )
    }

    fun clearStates() {
        _loginResponse.value = null
    }

}