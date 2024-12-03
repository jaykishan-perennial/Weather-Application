package com.example.openweatherapp.domain.repository

interface ResourceProviderRepository {
    fun getString(resId: Int): String
}
