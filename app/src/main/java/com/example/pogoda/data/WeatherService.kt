package com.example.pogoda.data


import com.example.WeatherForSpecificDays.WeatherForSpecificDays
import com.example.pogoda.Models.WeatherRealTime.WeatherApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface WeatherService {
    @GET("current.json")
    fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") airQuality: String = "yes"
    ): Call<WeatherApi>

    @GET("marine.json")
    fun getWeatherDay(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") date: Int,
    ): Call<WeatherForSpecificDays>
}