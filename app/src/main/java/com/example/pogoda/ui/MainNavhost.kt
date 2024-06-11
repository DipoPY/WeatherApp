package com.example.pogoda.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pogoda.Models.WeatherRealTime.AirQuality

@Composable
fun MainNavHost(weatherViewModel: WeatherViewModel) {
    val navController = rememberNavController()
    val airQuality: AirQuality? = weatherViewModel.weatherData?.current?.airQuality

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(weatherViewModel, navController, airQuality)
        }
        composable("details") {
            AirDetailsScreen(weatherViewModel, navController,airQuality)
        }
    }
}