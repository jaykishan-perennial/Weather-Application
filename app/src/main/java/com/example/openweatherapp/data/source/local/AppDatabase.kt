package com.example.openweatherapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.local.dao.WeatherDao
import com.example.openweatherapp.data.source.local.entity.UserEntity
import com.example.openweatherapp.data.source.local.entity.WeatherEntity

@Database(entities = [UserEntity::class, WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authDao(): AuthDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DB_NAME = "WeatherApp.db"
    }
}