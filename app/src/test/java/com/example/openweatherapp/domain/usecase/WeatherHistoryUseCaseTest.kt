package com.example.openweatherapp.domain.usecase

import com.example.openweatherapp.data.source.local.entity.WeatherEntity
import com.example.openweatherapp.domain.repository.WeatherRepository
import com.example.openweatherapp.utils.WeatherMockUtils
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherHistoryUseCaseTest {

    private var weatherRepository: WeatherRepository = mockk()
    private lateinit var weatherHistoryUseCase: WeatherHistoryUseCase

    @Before
    fun setUp(){
        weatherHistoryUseCase = WeatherHistoryUseCase(weatherRepository)
    }

    @Test
    fun `Successful weather history retrieval`() = runTest {
        coEvery {
            weatherRepository.getWeatherHistory()
        } returns WeatherMockUtils.weatherHistoryEntities

        val result = weatherHistoryUseCase.invoke()
        assert(result.isNotEmpty())
    }

    @Test
    fun `Skip null entries in list mapping`() = runTest {
        coEvery {
            weatherRepository.getWeatherHistory()
        } returns listOf<WeatherEntity?>(null, null, null)

        val result = weatherHistoryUseCase.invoke()
        assert(result.isEmpty())
    }

}