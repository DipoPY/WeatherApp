package com.example.pogoda.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.WeatherForSpecificDays.WeatherForSpecificDays
import com.example.pogoda.Models.WeatherRealTime.WeatherApi
import com.example.pogoda.data.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class WeatherViewModel : ViewModel() {
    var weatherData by mutableStateOf<WeatherApi?>(null)
        private set

    var weatherDays by mutableStateOf<WeatherForSpecificDays?>(null)
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    val API_KEY: String = "903c1a3eb1ae4fb3a7081508242705"
    val BASE_URL: String = "https://api.weatherapi.com/v1/"

    private val weatherService = RetrofitClient.getWeatherService(BASE_URL)

    fun fetchWeatherRealTime(location: String) {
        isLoading = true
        errorMessage = null
        val apiKey: String = API_KEY
        viewModelScope.launch {
            weatherService.getWeather(apiKey, location)
                .enqueue(object : Callback<WeatherApi> {
                    override fun onResponse(call: Call<WeatherApi>, response: Response<WeatherApi>) {
                        isLoading = false
                        if (response.isSuccessful) {
                            weatherData = response.body()
                            Log.d("WeatherViewModel", "Real-time weather data: $weatherData")
                        } else {
                            errorMessage = "Error: ${response.message()}"
                            Log.e("WeatherViewModel", "Error fetching real-time weather: ${response.errorBody()?.string()}")
                        }
                    }
                    override fun onFailure(call: Call<WeatherApi>, t: Throwable) {
                        isLoading = false
                        errorMessage = t.message
                        Log.e("WeatherViewModel", "Failed to fetch real-time weather", t)
                    }
                })
        }
    }
    //@RequiresApi(Build.VERSION_CODES.O) //зачем тут ограничение по API, я пока сотру
    fun fetchWeatherForThreeDays(location: String) {
        isLoading = true
        errorMessage = null
        val apiKey: String = API_KEY
        viewModelScope.launch {
            val date = 3
            Log.d("WeatherViewModel", "Fetching weather for date: $date")
            weatherService.getWeatherDay(apiKey, location, date)
                .enqueue(object : Callback<WeatherForSpecificDays> {
                    override fun onResponse(call: Call<WeatherForSpecificDays>, response: Response<WeatherForSpecificDays>) {
                        isLoading = false
                        if (response.isSuccessful) {
                            weatherDays = response.body()
                            Log.d("WeatherViewModel", "Weather for specific days data: $weatherDays")
                        } else {
                            errorMessage = "Error: ${response.message()}"
                            Log.e("WeatherViewModel", "Error fetching weather for specific days: ${response.errorBody()?.string()}")
                        }
                    }
                    override fun onFailure(call: Call<WeatherForSpecificDays>, t: Throwable) {
                        isLoading = false
                        errorMessage = t.message
                        Log.e("WeatherViewModel", "Failed to fetch weather for specific days", t)
                    }
                })
        }
    }
}
