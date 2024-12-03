package com.example.openweatherapp.utils.extension


import android.util.Log
import com.example.openweatherapp.BuildConfig

fun String.logd(tag: String = "Log-DEBUG") {
    if (BuildConfig.DEBUG)
        Log.d(tag, this)
}

fun String.loge(tag: String = "Log-ERROR") {
    if (BuildConfig.DEBUG)
        Log.e(tag, this)
}

fun String.logv(tag: String = "Log-VERBOSE") {
    if (BuildConfig.DEBUG)
        Log.v(tag, this)
}

fun String.logi(tag: String = "Log-INFORMATION") {
    if (BuildConfig.DEBUG)
        Log.i(tag, this)
}

fun String.logw(tag: String = "Log-WARNING") {
    if (BuildConfig.DEBUG)
        Log.w(tag, this)
}