package com.example.openweatherapp.ui.auth.utils.userValidation

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class EmailValidatorTest {

    private lateinit var emailValidator: EmailValidator

    @Before
    fun setup() {
        emailValidator = EmailValidator()
    }

    @Test
    fun `validate returns true for valid email`() {
        val email = "test@example.com"
        val result = emailValidator.validate(email)
        assertEquals(true, result)
    }

    @Test
    fun `validate returns false for null email`() {
        val email: String? = null
        val result = emailValidator.validate(email)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for empty email`() {
        val email = ""
        val result = emailValidator.validate(email)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for invalid email without domain`() {
        val email = "test@"
        val result = emailValidator.validate(email)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns false for invalid email with special characters`() {
        val email = "test@@example.com"
        val result = emailValidator.validate(email)
        assertEquals(false, result)
    }

    @Test
    fun `validate returns true for email with subdomains`() {
        val email = "user@mail.example.com"
        val result = emailValidator.validate(email)
        assertEquals(true, result)
    }

    @Test
    fun `validate returns false for email with spaces`() {
        val email = "test @example.com"
        val result = emailValidator.validate(email)
        assertEquals(false, result)
    }
}