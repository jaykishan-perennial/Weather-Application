package com.example.openweatherapp.data.repositoryImpl

import com.example.openweatherapp.BuildConfig
import com.example.openweatherapp.data.source.local.dao.WeatherDao
import com.example.openweatherapp.data.source.local.entity.WeatherEntity
import com.example.openweatherapp.data.source.remote.ApiService
import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.domain.repository.WeatherRepository
import com.example.openweatherapp.utils.extension.loge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "WeatherRepositoryImpl"

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): WeatherInfo? {
        return withContext(Dispatchers.IO){
            val weatherInfo = try {
                apiService.getCurrentWeather(
                    lat = lat, lon = lon, apiKey = BuildConfig.OPEN_WEATHER_API_KEY
                )
            }catch (e: Exception){
                null
            }

            weatherInfo?.let {
                try {
                    weatherDao.insertWeatherData(it.toWeatherEntity())
                }catch (e: Exception){
                }
            }
            weatherInfo
        }
    }

    override suspend fun getWeatherHistory(): List<WeatherEntity> {
        return withContext(Dispatchers.IO){
            try {
                weatherDao.getWeatherHistory()
            }catch (e: Exception){
                "getWeatherHistory: $e".loge(TAG)
                emptyList()
            }
        }
    }

}