package com.example.pogoda.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Models.WeatherApi
import com.example.pogoda.data.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {
    var weatherData by mutableStateOf<WeatherApi?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    val API_KEY: String = "eae034f36b2142bfa5d141613242205"
    val BASE_URL: String = "https://api.weatherapi.com/v1/"

    private val weatherService = RetrofitClient.getWeatherService(BASE_URL)

    fun fetchWeather(location: String) {
        isLoading = true
        errorMessage = null
        val apiKey: String = API_KEY
        viewModelScope.launch {
            weatherService.getWeather(apiKey, location).enqueue(object : Callback<WeatherApi> {
                override fun onResponse(call: Call<WeatherApi>, response: Response<WeatherApi>) {
                    isLoading = false
                    if (response.isSuccessful) {
                        weatherData = response.body()
                    } else {
                        errorMessage = "Error: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<WeatherApi>, t: Throwable) {
                    isLoading = false
                    errorMessage = t.message
                    t.printStackTrace()
                }
            })
        }
    }
}
