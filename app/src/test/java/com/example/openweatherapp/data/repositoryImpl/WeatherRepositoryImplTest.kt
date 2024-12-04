package com.example.openweatherapp.data.repositoryImpl

import com.example.openweatherapp.data.source.local.dao.WeatherDao
import com.example.openweatherapp.data.source.remote.ApiService
import com.example.openweatherapp.utils.WeatherMockUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class WeatherRepositoryImplTest {

    private var mockApiService: ApiService = mockk()

    private var mockWeatherDao: WeatherDao = mockk(relaxed = true)

    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        repository = WeatherRepositoryImpl(mockApiService, mockWeatherDao)
    }

    @Test
    fun `getCurrentWeather should fetch weather data and save to database on success`() = runTest {

        val weatherInfo = WeatherMockUtils.weatherInfo

        coEvery {
            mockApiService.getCurrentWeather(
                WeatherMockUtils.latitude,
                WeatherMockUtils.longitude,
                any()
            )
        } returns weatherInfo

        val result = repository.getCurrentWeather(
            WeatherMockUtils.latitude,
            WeatherMockUtils.longitude,
            WeatherMockUtils.mockApiKey
        )

        assertEquals(weatherInfo, result)
        coVerify(exactly = 1) {
            mockApiService.getCurrentWeather(
                WeatherMockUtils.latitude,
                WeatherMockUtils.longitude,
                any()
            )
        }
    }

    @Test
    fun `getCurrentWeather should return null when API call fails`() = runTest {
        val repository = WeatherRepositoryImpl(mockApiService, mockWeatherDao)

        coEvery {
            mockApiService.getCurrentWeather(
                WeatherMockUtils.latitude,
                WeatherMockUtils.longitude,
                any()
            )
        } throws Exception("Network error")

        val result = repository.getCurrentWeather(
            WeatherMockUtils.latitude,
            WeatherMockUtils.longitude,
            WeatherMockUtils.mockApiKey
        )

        Assert.assertNull(result)
        coVerify(exactly = 1) {
            mockApiService.getCurrentWeather(
                WeatherMockUtils.latitude,
                WeatherMockUtils.longitude,
                any()
            )
        }
        coVerify(exactly = 0) { mockWeatherDao.insertWeatherData(any()) }
    }

    @Test
    fun `getCurrentWeather should not crash when database insertion fails`() = runTest {
        val repository = WeatherRepositoryImpl(mockApiService, mockWeatherDao)


        val weatherInfo = WeatherMockUtils.weatherInfo

        coEvery {
            mockApiService.getCurrentWeather(
                WeatherMockUtils.latitude,
                WeatherMockUtils.longitude,
                any()
            )
        } returns weatherInfo
        coEvery { mockWeatherDao.insertWeatherData(any()) } throws WeatherMockUtils.exception

        val result = repository.getCurrentWeather(
            WeatherMockUtils.latitude,
            WeatherMockUtils.longitude,
            WeatherMockUtils.mockApiKey
        )

        assertEquals(weatherInfo, result)
        coVerify(exactly = 1) {
            mockApiService.getCurrentWeather(
                WeatherMockUtils.latitude,
                WeatherMockUtils.longitude,
                any()
            )
        }
        coVerify(exactly = 1) { mockWeatherDao.insertWeatherData(any()) }
    }

    @Test
    fun `getWeatherHistory should return data from database`() = runTest {
        val repository = WeatherRepositoryImpl(mockApiService, mockWeatherDao)

        val weatherHistory = listOf(WeatherMockUtils.expectedWeatherEntity)

        coEvery { mockWeatherDao.getWeatherHistory() } returns weatherHistory

        val result = repository.getWeatherHistory()

        assertEquals(weatherHistory, result)
        coVerify(exactly = 1) { mockWeatherDao.getWeatherHistory() }
    }

    @Test
    fun `getWeatherHistory should return empty list on database failure`() = runTest {
        coEvery { mockWeatherDao.getWeatherHistory() } returns emptyList()
        val result = repository.getWeatherHistory()
        assertTrue(result.isEmpty())
    }

}