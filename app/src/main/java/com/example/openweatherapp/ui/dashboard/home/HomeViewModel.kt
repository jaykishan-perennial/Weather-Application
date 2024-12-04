package com.example.openweatherapp.ui.dashboard.home

import android.animation.ValueAnimator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.domain.repository.WeatherRepository
import com.example.openweatherapp.domain.usecase.CurrentWeatherUseCase
import com.example.openweatherapp.utils.utility.LocationHelper
import com.example.openweatherapp.utils.utility.NetworkConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val networkConnectionManager: NetworkConnectionManager,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo : LiveData<WeatherInfo> = _weatherInfo

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isOffline = MutableLiveData<Boolean>()
    val isOffline : LiveData<Boolean> = _isOffline

    private val _sunsetProgress = MutableLiveData<Int>(0)
    val sunsetProgress : LiveData<Int> = _sunsetProgress

    fun isNetworkAvailable(): Boolean {
        return networkConnectionManager.isNetworkAvailable().also {
            _isOffline.value = it.not()
        }
    }

    fun locationServiceEnabled() = locationHelper.isLocationEnabled()

    fun onRetry() {
        viewModelScope.launch {
            _isLoading.value = true
            if (isNetworkAvailable().not()){
                delay(1000) // For stable user experience
                _isLoading.value = false
                return@launch
            }
            getWeatherInfo()
        }
    }

    suspend fun getWeatherInfo() {
        if (isNetworkAvailable().not()){
            return
        }

        _isLoading.value = true

        val location = locationHelper.requestLocationUpdates().firstOrNull()

        if (location == null){
            _isLoading.value = false
            return
        }

        val response = currentWeatherUseCase.invoke(
            lat = location.latitude,
            lon = location.longitude
        )

        response?.let {
            _weatherInfo.value = it

            // Animate sunset progress
            val maxDuration = 2500L
            val totalProgress = it.sys.sunset - it.sys.sunrise
            val remainingProgress = it.sys.sunset - it.dt
            val currentProgress = totalProgress - remainingProgress
            val progress = (currentProgress * 100) / totalProgress

            if (progress < 0){
                _sunsetProgress.value = 100
            }else{
                ValueAnimator.ofInt(0, progress.toInt()).apply {
                    duration = (maxDuration * progress / 100)
                    addUpdateListener { animation ->
                        _sunsetProgress.postValue(animation.animatedValue as Int)
                    }
                    start()
                }
            }

        }
        _isLoading.value = false
    }

}