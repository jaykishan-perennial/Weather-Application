package com.example.openweatherapp.domain.repository

import com.example.openweatherapp.data.source.remote.WeatherInfo

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherInfo?

    suspend fun getWeatherHistory(): List<WeatherInfo>
}