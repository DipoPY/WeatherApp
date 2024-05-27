package com.example.pogoda.ui

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.pogoda.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(weatherViewModel: WeatherViewModel) {
    val weatherData = weatherViewModel.weatherData
    val isLoading = weatherViewModel.isLoading
    val errorMessage = weatherViewModel.errorMessage
    val weatherDays = weatherViewModel.weatherDays

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBarMainScreen() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgraund_for_main_screen),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            when {
                isLoading -> {
                    Text(text = "Loading...", color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    Text(text = "Error: $errorMessage", color = Color.Red, modifier = Modifier.align(Alignment.Center))

                }
                weatherData != null -> {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(text = "Location: ${weatherData?.location?.name}", color = Color.White)
                        Text(text = "Temperature: ${weatherData?.current?.tempC} °C", color = Color.White)
                        Text(text = "Condition: ${weatherData?.current?.condition?.text}", color = Color.White)
                        Text(text = "--------------------------")
                        Text(text = weatherDays?.location?.name.orEmpty(), color = Color.White)
                        weatherDays?.forecast?.forecastday?.forEach { forecastDay ->
                            Text(text = forecastDay.date.orEmpty(), color = Color.White)
                            Text(text = "Max temp: ${forecastDay.day?.maxtempC ?: "N/A"} °C", color = Color.White)
                            Text(text = "Min temp: ${forecastDay.day?.mintempC ?: "N/A"} °C", color = Color.White)
                        }
                    }
                }
            }
            Log.d("MainScreen", "Weather Data: ${weatherData?.location?.name}")
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMainScreen(){
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Z")
            }},
        navigationIcon = {
            IconButton({ }) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить")
            }},
        actions = {
            IconButton({ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Меню")
            }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,
            titleContentColor = Color.White,
        )
    )
}

@Preview
@Composable
fun textBox(){
    Box(
        modifier = Modifier.fillMaxWidth(),

    ){
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(Icons.Filled.AccountBox, contentDescription = "Знак погоды")
            Text(text = "Сегодня")
            Text(text = "Ясно")

        }
    }
}