package com.example.openweatherapp.ui.auth.utils.userValidation

import android.util.Patterns
import javax.inject.Inject

class EmailValidator @Inject constructor() {

    /**
     * This method checks if emailed being passed in parameter is valid or not and return true/false.
     */
    fun validate(email: String?): Boolean {
        if (email.isNullOrEmpty()) return false
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}