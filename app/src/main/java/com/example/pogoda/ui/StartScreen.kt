package com.example.pogoda.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pogoda.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(weatherViewModel: WeatherViewModel) {
    val weatherData = weatherViewModel.weatherData
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainTopBar(weatherViewModel) },
    )
    {
        Background("${weatherData?.current?.condition?.text}")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Temperature(weatherViewModel)
            FutureTemperature(weatherViewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(weatherViewModel: WeatherViewModel) {
    val weatherData = weatherViewModel.weatherData
    val isLoading = weatherViewModel.isLoading
    val errorMessage = weatherViewModel.errorMessage

    TopAppBar(
        modifier = Modifier.height(56.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,
            titleContentColor = Color.White
        ),
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        Text(text = "Loading...", color = Color.White)
                    }
                    errorMessage != null -> {
                        Text(text = "Error: $errorMessage", color = Color.Red)
                    }
                    weatherData != null -> {
                        Column {
                            Text(text = "${weatherData?.location?.name}", color = Color.White)
                        }
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation icon click */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        actions = {
            IconButton(onClick = { /* Handle action icon click */ }) {
                Icon(Icons.Filled.List, contentDescription = "Menu")
            }
        }
    )
}

@Composable
fun FutureTemperature(weatherViewModel: WeatherViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DataFutureTemperature(weatherViewModel)
    }
}

@Composable
fun Temperature(weatherViewModel: WeatherViewModel){

    val weatherData = weatherViewModel.weatherData
    val isLoading = weatherViewModel.isLoading
    val errorMessage = weatherViewModel.errorMessage

    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
    ){
        when {
            isLoading -> {
                Text(text = "Loading...", color = Color.White, modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                Text(text = "Error: $errorMessage", color = Color.Red, modifier = Modifier.align(Alignment.Center))
            }
            weatherData != null -> {
                Column(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                        Text(
                            text = "${(weatherData?.current?.tempC?.toInt())}°C",
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(text = "${weatherData?.current?.condition?.text}", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun DataFutureTemperature(weatherViewModel: WeatherViewModel){
    val weatherDays = weatherViewModel.weatherDays
    Box(
        modifier = Modifier
            .fillMaxHeight(0.33f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = weatherDays?.location?.name.orEmpty(), color = Color.White)
            weatherDays?.forecast?.forecastday?.forEach { forecastDay ->
                Text(text = forecastDay.date.orEmpty(), color = Color.White)
                Text(
                    text = "Max temp: ${forecastDay.day?.maxtempC ?: "N/A"} °C",
                    color = Color.White
                )
                Text(
                    text = "Min temp: ${forecastDay.day?.mintempC ?: "N/A"} °C",
                    color = Color.White
                )
            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = weatherDays?.location?.name.orEmpty(), color = Color.White)
            weatherDays?.forecast?.forecastday?.forEach { forecastDay ->
                Text(text = forecastDay.date.orEmpty(), color = Color.White)
                Text(
                    text = "Max temp: ${forecastDay.day?.maxtempC ?: "N/A"} °C",
                    color = Color.White
                )
                Text(
                    text = "Min temp: ${forecastDay.day?.mintempC ?: "N/A"} °C",
                    color = Color.White
                )
            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = weatherDays?.location?.name.orEmpty(), color = Color.White)
            weatherDays?.forecast?.forecastday?.forEach { forecastDay ->
                Text(text = forecastDay.date.orEmpty(), color = Color.White)
                Text(
                    text = "Max temp: ${forecastDay.day?.maxtempC ?: "N/A"} °C",
                    color = Color.White
                )
                Text(
                    text = "Min temp: ${forecastDay.day?.mintempC ?: "N/A"} °C",
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun Background(weatherCondition:String){
    Box(modifier = Modifier.fillMaxSize()){
        val backgroundResource = when(weatherCondition){
            "Sunny" -> R.drawable.sunny_background
            "Rainy" -> R.drawable.rainy_background
            "Cloudy" -> R.drawable.cloudy_background
            "Snowy" -> R.drawable.snowy_background
            else -> R.drawable.default_background
        }
        Image(painter = painterResource(id = backgroundResource),
            contentDescription = "Задний фон",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize())
    }
}
