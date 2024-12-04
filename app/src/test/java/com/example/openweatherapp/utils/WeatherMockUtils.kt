package com.example.openweatherapp.utils

import com.example.openweatherapp.data.source.local.entity.WeatherEntity
import com.example.openweatherapp.data.source.remote.Main
import com.example.openweatherapp.data.source.remote.Sys
import com.example.openweatherapp.data.source.remote.Weather
import com.example.openweatherapp.data.source.remote.WeatherInfo

object WeatherMockUtils {

    val latitude = 37.7749
    val longitude = -122.4194
    val mockApiKey = "testApiKey"
    val exception = Exception("Database error")

    val weatherInfo = WeatherInfo(
        weather = listOf(Weather(1, "Clear", "clear sky", "01d")),
        main = Main(temp = 25.0, feels_like = 27.0, temp_min = 22.0, temp_max = 28.0),
        dt = 1625156400,
        sys = Sys(id = 1, country = "US", sunrise = 1625125200, sunset = 1625175600),
        id = 123,
        name = "TestCity"
    )

    val expectedWeatherEntity = WeatherEntity(
        main = "Clear",
        icon = "01d",
        name = "TestCity",
        country = "US",
        sunrise = 1625125200,
        sunset = 1625175600,
        dt = 1625156400,
        temp = 25.0,
        temp_min = 22.0,
        temp_max = 28.0,
        feels_like = 27.0
    )
    // If you want to test the `toWeatherEntity` method:
    val weatherEntity = weatherInfo.toWeatherEntity()

    // Similarly, you can use `fromWeatherEntity` to get back a `WeatherInfo` object.
    val newWeatherInfo = WeatherInfo.fromWeatherEntity(weatherEntity)

    val weatherHistoryEntities = listOf(
        weatherEntity,
        weatherEntity,
        weatherEntity,
    )

    val weatherHistoryInfo = listOf(
        weatherInfo,
        weatherInfo,
        weatherInfo,
    )
}