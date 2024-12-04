package com.example.openweatherapp.utils.databinding

import android.content.Context
import androidx.annotation.StringRes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DataBindingHelper {

    @JvmStatic
    fun notNullOrEmpty(vararg strings: String?): Boolean {
        return strings.all {
            it.isNullOrEmpty().not()
        }
    }

    @JvmStatic
    fun all(vararg booleans: Boolean): Boolean {
        return booleans.all { it }
    }

    @JvmStatic
    fun timeStringFromLong(millis : Long) : String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(Date(millis * 1000))
    }

    @JvmStatic
    fun dateTimeStringFromLong(millis : Long) : String {
        val formatter = SimpleDateFormat("MMM dd yyyy 'at' hh:mm a", Locale.getDefault())
        return formatter.format(Date(millis * 1000))
    }

    @JvmStatic
    fun kelvinToCelsius(kelvin : Double) : String {
        return (kelvin - 273.15).toInt().toString()
    }

    @JvmStatic
    fun getCountryName(countryCode: String?): String {
        if (countryCode == null) return ""
        return Locale("", countryCode).displayCountry
    }

    @JvmStatic
    fun getStringRes(context: Context, @StringRes resId: Int): String {
        return context.getString(resId)
    }

}