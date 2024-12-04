package com.example.openweatherapp.ui.auth.utils.userValidation

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class PasswordValidatorTest {
    private lateinit var passwordValidator: PasswordValidator

    @Before
    fun setup() {
        passwordValidator = PasswordValidator()
    }

    @Test
    fun `validate returns false for null password`() {
        val password: String? = null
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for empty password`() {
        val password = ""
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for password shorter than 8 characters`() {
        val password = "Pass1!"
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for password longer than 16 characters`() {
        val password = "Password12345!@#1234"
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for password without a digit`() {
        val password = "Password@"
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for password without an uppercase character`() {
        val password = "password1@"
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for password without a special symbol`() {
        val password = "Password123"
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for password with whitespace`() {
        val password = "Pass word1@"
        val result = passwordValidator.validate(password)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns true for valid password`() {
        val password = "Passw0rd@"
        val result = passwordValidator.validate(password)
        assertEquals(true, result)
    }
}