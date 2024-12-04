package com.example.openweatherapp.ui.auth.utils.userValidation

import android.util.Patterns
import javax.inject.Inject

class EmailValidator @Inject constructor() {

    /**
     * This method checks if emailed being passed in parameter is valid or not and return true/false.
     */
    private val emailRegex = ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+").toRegex()
    fun validate(email: String?): Boolean {
        if (email.isNullOrEmpty()) return false
        return email.matches(emailRegex)
    }

}