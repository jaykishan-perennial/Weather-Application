package com.example.openweatherapp.utils.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(toastMessage: String) {
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes toastMessage: Int) {
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}