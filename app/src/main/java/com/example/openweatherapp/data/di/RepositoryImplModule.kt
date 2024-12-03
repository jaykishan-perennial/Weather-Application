package com.example.openweatherapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import com.example.openweatherapp.domain.repository.LocalAuthRepository
import com.example.openweatherapp.data.repositoryImpl.LocalAuthRepositoryImpl
import com.example.openweatherapp.data.source.local.dao.AuthDao


@Module
@InstallIn(SingletonComponent::class)
class RepositoryImplModule {

    @Provides
    @Singleton
    fun provideLocalAuthRepository(authDao: AuthDao): LocalAuthRepository {
        return LocalAuthRepositoryImpl(authDao)
    }

}