package com.example.openweatherapp.data.source.remote

import com.example.openweatherapp.data.source.local.entity.WeatherEntity

data class WeatherInfo(
    val weather: List<Weather>,
    val main: Main,
    val dt: Long,
    val sys: Sys,
    val id: Int,
    val name: String
) {
    fun toWeatherEntity(): WeatherEntity {
        val weatherItem = weather[0]
        return WeatherEntity(
            main = weatherItem.main,
            icon = weatherItem.icon,
            name = name,
            country = sys.country,
            sunrise = sys.sunrise,
            sunset = sys.sunset,
            dt = dt,
            temp = main.temp,
            temp_min = main.temp_min,
            temp_max = main.temp_max,
            feels_like = main.feels_like
        )
    }

    companion object {
        fun fromWeatherEntity(weatherEntity: WeatherEntity?): WeatherInfo? {
            if (weatherEntity == null) return null
            return with(weatherEntity) {
                WeatherInfo(
                    weather = listOf(Weather(id, main, "", icon)),
                    main = Main(temp, feels_like, temp_min, temp_max),
                    dt = dt,
                    sys = Sys(0, country, sunrise, sunset),
                    id = id,
                    name = name,
                )
            }
        }
    }
}

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
)

data class Sys(
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
