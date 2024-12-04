package com.example.openweatherapp.ui.dashboard.weather_history

import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.domain.usecase.WeatherHistoryUseCase
import com.example.openweatherapp.utils.MainDispatcherRule
import com.example.openweatherapp.utils.WeatherMockUtils
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherHistoryViewModelTest {

    private val weatherHistoryUseCase : WeatherHistoryUseCase = mockk()
    lateinit var weatherHistoryViewModel : WeatherHistoryViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setUp(){
        weatherHistoryViewModel = WeatherHistoryViewModel(weatherHistoryUseCase)
    }

    @Test
    fun `getWeatherHistory success scenario`() {
        coEvery {
            weatherHistoryUseCase.invoke()
        } returns WeatherMockUtils.weatherHistoryInfo

        weatherHistoryViewModel.getWeatherHistory()

        assert(weatherHistoryViewModel.weatherHistory.value == WeatherMockUtils.weatherHistoryInfo)
    }

    @Test
    fun `getWeatherHistory empty list scenario`() {
        coEvery {
            weatherHistoryUseCase.invoke()
        } returns emptyList()

        weatherHistoryViewModel.getWeatherHistory()

        Assert.assertEquals(
            weatherHistoryViewModel.weatherHistory.value,
            emptyList<WeatherInfo>()
        )
    }

}