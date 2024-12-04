package com.example.openweatherapp.data.source.preference

import javax.inject.Inject
import javax.inject.Singleton
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

@Singleton
class LocalPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun <T> setData(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    fun <T> getData(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
                preferences -> preferences[key]
        }
    }
}

object PrefsKeys {
    val isLoggedIn = booleanPreferencesKey("isLoggedIn")
}