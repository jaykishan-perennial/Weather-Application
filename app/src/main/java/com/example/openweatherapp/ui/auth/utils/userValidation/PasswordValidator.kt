package com.example.openweatherapp.ui.auth.utils.userValidation

import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    /**
     * This method checks if password being passed in parameter is strong or not.
     */
    fun validate(password: String?): Boolean {
        if (password.isNullOrBlank()) return false

        if (password.length !in 8..16) return false

        var hasDigit = false
        var hasUppercaseChar = false
        var hasLowercaseChar = false
        var hasSpecialSymbol = false
        var hasWhiteSpace = false

        password.forEach {
            if (it.isDigit()) {
                hasDigit = true
            } else if (it.isUpperCase()) {
                hasUppercaseChar = true
            } else if (it.isWhitespace()) {
                hasWhiteSpace = true
            } else if (it.isLowerCase()) {
                hasLowercaseChar = true
            } else {
                hasSpecialSymbol = true
            }
        }

        return hasDigit && hasSpecialSymbol && hasUppercaseChar && hasWhiteSpace.not() && hasLowercaseChar
    }
}