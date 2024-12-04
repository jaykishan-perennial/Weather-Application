package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.BuildConfig
import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): WeatherInfo? {
        val apiKey = BuildConfig.OPEN_WEATHER_API_KEY
        return weatherRepository.getCurrentWeather(lat, lon, apiKey)
    }
}