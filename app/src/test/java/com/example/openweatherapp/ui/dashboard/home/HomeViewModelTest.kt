package com.example.openweatherapp.ui.dashboard.home

import android.location.Location
import com.example.openweatherapp.domain.usecase.CurrentWeatherUseCase
import com.example.openweatherapp.utils.MainDispatcherRule
import com.example.openweatherapp.utils.WeatherMockUtils
import com.example.openweatherapp.utils.utility.LocationHelper
import com.example.openweatherapp.utils.utility.NetworkConnectionManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val currentWeatherUseCase: CurrentWeatherUseCase = mockk()
    private val networkConnectionManager: NetworkConnectionManager = mockk()
    private val locationHelper: LocationHelper = mockk()

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(currentWeatherUseCase, networkConnectionManager, locationHelper)
    }

    @Test
    fun `isNetworkAvailable returns true when network is available`() {
        coEvery { networkConnectionManager.isNetworkAvailable() } returns false
        viewModel.isNetworkAvailable()
        coVerify { viewModel.isNetworkAvailable() }
        Assert.assertEquals(true, viewModel.isOffline.value)
    }

    @Test
    fun `onRetry sets isOffline true when network is unavailable`() = runTest {
        coEvery { networkConnectionManager.isNetworkAvailable() } returns false
        viewModel.onRetry()
        Assert.assertEquals(true, viewModel.isOffline.value)

        val mockLocation = mockk<Location>("mockkProvider").apply {
            coEvery { latitude } returns 37.422
            coEvery { longitude } returns -122.084
        }

        coEvery { locationHelper.requestLocationUpdates().firstOrNull() } returns mockLocation

        coEvery { currentWeatherUseCase.invoke(mockLocation.latitude, mockLocation.longitude) } returns WeatherMockUtils.weatherInfo

        viewModel.getWeatherInfo()
    }


    @Test
    fun `getWeatherInfo sets isLoading false when location is available`() = runTest {
        coEvery { networkConnectionManager.isNetworkAvailable() } returns true
        viewModel.isNetworkAvailable()
        coVerify { viewModel.isNetworkAvailable() }

        val mockLocation = mockk<Location>("mockkProvider").apply {
            coEvery { latitude } returns 37.422
            coEvery { longitude } returns -122.084
        }

        coEvery { locationHelper.requestLocationUpdates() } returns flowOf(mockLocation)

        coEvery { currentWeatherUseCase.invoke(mockLocation.latitude, mockLocation.longitude) } returns WeatherMockUtils.weatherInfo
    }

}