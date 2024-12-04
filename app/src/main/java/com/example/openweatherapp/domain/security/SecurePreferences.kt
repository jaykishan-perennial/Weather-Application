package com.example.openweatherapp.domain.security

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.openweatherapp.BuildConfig
import com.example.openweatherapp.data.source.preference.LocalPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SecurePreferences @Inject constructor(
    private val localPreferences: LocalPreferences,
    private val securityUtil: SecurityUtil,
    private val gson: Gson
) {

    val bytesToStringSeparator = "|"
    val keyAlias = BuildConfig.SHARED_PREF_KEY_ALIAS
    val ivToStringSeparator= ":iv:"

    suspend fun <T> setData(key: Preferences.Key<T>, value: T) {
        val serializedInput = gson.toJson(value)
        val (iv, secureByteArray) = securityUtil.encryptData(keyAlias, serializedInput)
        val secureString = iv.joinToString(bytesToStringSeparator) + ivToStringSeparator + secureByteArray.joinToString(bytesToStringSeparator)
        localPreferences.setData(stringPreferencesKey(key.name), secureString)
    }

    fun <T> getData(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return localPreferences.getData(stringPreferencesKey(key.name))
            .map { secureString ->
                if (secureString == null) return@map defaultValue
                val (ivString, encryptedString) = secureString.split(ivToStringSeparator, limit = 2)
                val iv = ivString.split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
                val encryptedData = encryptedString.split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
                val decryptedValue = securityUtil.decryptData(keyAlias, iv, encryptedData)
                val type = object : TypeToken<T>() {}.type
                gson.fromJson(decryptedValue, type) as T
            }
    }

}