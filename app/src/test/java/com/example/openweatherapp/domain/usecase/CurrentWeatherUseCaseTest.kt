package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.domain.repository.WeatherRepository
import com.example.openweatherapp.utils.WeatherMockUtils
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class CurrentWeatherUseCaseTest {

    private var weatherRepository: WeatherRepository = mockk()

    lateinit var currentWeatherUseCase: CurrentWeatherUseCase

    @Before
    fun setUp() {
        currentWeatherUseCase = CurrentWeatherUseCase(weatherRepository)
    }

    @Test
    fun `getCurrentWeather returns weather info`() = runTest {
        coEvery {
            weatherRepository.getCurrentWeather(any(), any(), any())
        } returns WeatherMockUtils.weatherInfo

        val weatherInfo = currentWeatherUseCase.invoke(1.1, 1.1)
        assert(weatherInfo == WeatherMockUtils.weatherInfo)
    }

    @Test
    fun `getCurrentWeather returns null when repository returns null`() = runTest {
        coEvery {
            weatherRepository.getCurrentWeather(any(), any(), any())
        } returns null

        val result = currentWeatherUseCase.invoke(25.2, 23.3)
        Assert.assertNull(result)
    }

}