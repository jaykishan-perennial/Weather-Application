package com.example.openweatherapp.data.repositoryImpl

import android.content.Context
import com.example.openweatherapp.domain.repository.ResourceProviderRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProviderRepositoryImpl @Inject constructor(private val context: Context) :
    ResourceProviderRepository {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}