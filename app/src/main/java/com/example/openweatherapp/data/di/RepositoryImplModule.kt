package com.example.openweatherapp.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.data.repositoryImpl.LocalAuthRepositoryImpl
import com.example.openweatherapp.data.repositoryImpl.ResourceProviderRepositoryImpl
import com.example.openweatherapp.data.repositoryImpl.WeatherRepositoryImpl
import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.remote.ApiService
import com.example.openweatherapp.domain.repository.ResourceProviderRepository
import com.example.openweatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
class RepositoryImplModule {

    @Provides
    @Singleton
    fun provideLocalAuthRepository(
        authDao: AuthDao, resourceStringRepository: ResourceProviderRepository
    ): LocalAuthRepository {
        return LocalAuthRepositoryImpl(authDao, resourceStringRepository)
    }

    @Provides
    @Singleton
    fun provideResourceProviderRepository(@ApplicationContext context: Context): ResourceProviderRepository {
        return ResourceProviderRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: ApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }

}