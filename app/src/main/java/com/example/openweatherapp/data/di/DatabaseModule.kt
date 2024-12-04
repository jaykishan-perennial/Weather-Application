package com.example.openweatherapp.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.openweatherapp.data.source.local.AppDatabase
import com.example.openweatherapp.data.source.local.UserDatabasePassphrase
import com.example.openweatherapp.data.source.local.dao.AuthDao
import com.example.openweatherapp.data.source.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabasePassphrase(@ApplicationContext context: Context) = UserDatabasePassphrase(context)

    @Provides
    @Singleton
    fun provideSupportFactory(userDatabasePassphrase: UserDatabasePassphrase) = SupportFactory(userDatabasePassphrase.getPassphrase())

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application, supportFactory: SupportFactory): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            .openHelperFactory(supportFactory)
            .allowMainThreadQueries().build()
    }

    @Provides
    internal fun provideAuthDao(appDatabase: AppDatabase): AuthDao {
        return appDatabase.authDao()
    }

    @Provides
    internal fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
}