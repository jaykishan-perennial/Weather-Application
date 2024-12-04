package com.example.openweatherapp.data.di

import com.example.openweatherapp.data.repositoryImpl.LocalAuthRepositoryImpl
import com.example.openweatherapp.data.repositoryImpl.WeatherRepositoryImpl
import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.local.dao.WeatherDao
import com.example.openweatherapp.data.source.remote.ApiService
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.domain.repository.WeatherRepository
import com.example.openweatherapp.domain.security.SecurePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryImplModule {

    @Provides
    @Singleton
    fun provideLocalAuthRepository(
        authDao: AuthDao, securePreferences: SecurePreferences
    ): LocalAuthRepository {
        return LocalAuthRepositoryImpl(authDao, securePreferences)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: ApiService, weatherDao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(apiService, weatherDao)
    }

}