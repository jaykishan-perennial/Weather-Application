package com.example.openweatherapp.ui.dashboard.weather_history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.domain.usecase.WeatherHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val weatherHistoryUseCase: WeatherHistoryUseCase
) : ViewModel() {

    private val _weatherHistory = MutableLiveData<List<WeatherInfo>>()
    val weatherHistory: MutableLiveData<List<WeatherInfo>> = _weatherHistory

    fun setWeatherHistory(weatherHistory: List<WeatherInfo>) {
        _weatherHistory.value = weatherHistory
    }

    fun getWeatherHistory() {
        viewModelScope.launch {
            val weatherHistory = weatherHistoryUseCase.invoke()
            setWeatherHistory(weatherHistory)
        }
    }

}