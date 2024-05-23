package com.example.pogoda.data

import com.example.Models.WeatherApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") airQuality: String = "yes"
    ): Call<WeatherApi>
}