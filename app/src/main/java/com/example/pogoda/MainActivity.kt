package com.example.pogoda

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.pogoda.ui.MainScreen
import com.example.pogoda.ui.WeatherViewModel
import com.example.pogoda.ui.theme.PogodaTheme

class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogodaTheme {
                MainScreen(weatherViewModel)
            }
        }
        weatherViewModel.fetchWeatherRealTime("Moscow")
        weatherViewModel.fetchWeatherForThreeDays("Moscow")
    }

}
