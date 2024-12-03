package com.example.openweatherapp.data.source.remote

import com.example.openweatherapp.domain.models.Weather24
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //todo please replace Weather24 with actual model class
    //todo change endpoint to actual one
    @GET("weather")
    suspend fun get24HourWeatherData(): Weather24

    //todo please replace Weather24 with actual model class
    //todo change endpoint to actual one
    @POST("weather")
    suspend fun postWeatherData()
}