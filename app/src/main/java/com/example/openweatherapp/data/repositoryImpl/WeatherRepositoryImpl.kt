package com.example.openweatherapp.data.repositoryImpl

import android.util.Log
import com.example.openweatherapp.BuildConfig
import com.example.openweatherapp.data.source.remote.ApiService
import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "WeatherRepositoryImpl"

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): WeatherInfo? {
        return withContext(Dispatchers.IO){
            try {
                apiService.getCurrentWeather(
                    lat = lat, lon = lon, apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                )
            }catch (e: Exception){
                Log.e(TAG, "getCurrentWeather: $e")
                null
            }
        }
    }
}