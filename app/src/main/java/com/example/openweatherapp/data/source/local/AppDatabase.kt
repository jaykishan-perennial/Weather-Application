package com.example.openweatherapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authDao(): AuthDao

    companion object {
        const val DB_NAME = "WeatherApp.db"
    }
}