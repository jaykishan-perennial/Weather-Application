package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherHistoryUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): List<WeatherInfo> {
        return weatherRepository
            .getWeatherHistory()
            .mapNotNull(WeatherInfo::fromWeatherEntity)
    }
}