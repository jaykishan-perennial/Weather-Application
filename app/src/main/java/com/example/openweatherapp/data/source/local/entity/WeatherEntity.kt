package com.example.openweatherapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val main: String,
    val icon: String,
    val name: String,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
    val dt : Long,
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val feels_like: Double
)