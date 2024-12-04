package com.example.openweatherapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.openweatherapp.data.source.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherEntity)

    @Query("SELECT * FROM weather_table order by dt desc")
    suspend fun getWeatherHistory(): List<WeatherEntity>

}