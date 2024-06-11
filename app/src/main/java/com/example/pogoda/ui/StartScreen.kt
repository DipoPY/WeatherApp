package com.example.pogoda.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pogoda.Models.WeatherRealTime.AirQuality
import com.example.pogoda.R
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun MinTextFormat(weatherCondition:String):String{
    val Resource = when{
        Regex("sun", RegexOption.IGNORE_CASE).containsMatchIn(weatherCondition)-> "Sunny"
        Regex("rain", RegexOption.IGNORE_CASE).containsMatchIn(weatherCondition)-> "Rainy"
        Regex("cloud", RegexOption.IGNORE_CASE).containsMatchIn(weatherCondition)-> "Cloudly"
        Regex("snow", RegexOption.IGNORE_CASE).containsMatchIn(weatherCondition) -> "Snowy"
        else -> "Default"
    }
    return Resource
}
@Composable
fun Background(weatherCondition:String){
    Box(modifier = Modifier.fillMaxSize()){
        val backgroundResource = when{
            MinTextFormat(weatherCondition)=="Sunny"->R.drawable.sunny_background
            MinTextFormat(weatherCondition)=="Rainy"-> R.drawable.rainy_background
            MinTextFormat(weatherCondition)=="Cloudly"-> R.drawable.cloudy_background
            MinTextFormat(weatherCondition)=="Snowy" -> R.drawable.snowy_background
            else ->R.drawable.default_background
        }
        Image(painter = painterResource(id = backgroundResource),
            contentDescription = "Задний фон",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize())
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    navController: NavController,
    airQuality: AirQuality?
) {
    val weatherData = weatherViewModel.weatherData
    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = MaterialTheme.colorScheme
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Blue, // Синий цвет для системных панелей
//            darkIcons = useDarkIcons // Устанавливаем цвет иконок в системных панелях
        )
    }
    ProvideWindowInsets {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            topBar = { MainTopBar(weatherViewModel) },
        ) {
            Background(weatherData?.current?.condition?.text ?: "")
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Temperature(weatherViewModel, navController, airQuality)
                FutureTemperature(weatherViewModel)
            }
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
                            Text(text = "${weatherData.location?.name}", color = Color.White)
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
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.DarkGray.copy(alpha = 0.7f))
    ) {
        DataFutureTemperature(weatherViewModel, modifier = Modifier.weight(1f))
    }
}
@Composable
fun DataFutureTemperature(weatherViewModel: WeatherViewModel, modifier: Modifier){
    val weatherDays = weatherViewModel.weatherDays
    weatherDays?.forecast?.forecastday?.forEach { forecastDay ->
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val formatedData = DataFormated(forecastDay.date)
                val DayCondition = forecastDay?.day?.condition?.text ?: "Im null"
                Text(
                    text = "${formatedData} ${
                        MinTextFormat(DayCondition)
                    }",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${forecastDay.day?.maxtempC?.toInt() ?: "N/A"}°/${forecastDay.day?.mintempC?.toInt() ?: "N/A"}°",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            Log.e("Data", "${forecastDay.date} ")
        }
    }
}
fun DataFormated(data: String?): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(data, formatter)
    val today = LocalDate.now()
    val tomorrow = today.plusDays(1)
    val dayAfterTomorrow = today.plusDays(2)
    var formatedData = "Ой"
    when(date){
        today -> formatedData="Сегодня"
        tomorrow -> formatedData ="Завтра"
        dayAfterTomorrow->{
            val dayName= dayAfterTomorrow.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale("ru"))
            formatedData = dayName.capitalize()
        }
    }
    return formatedData
}
@Composable
fun Temperature(
    weatherViewModel: WeatherViewModel,
    navController: NavController,
    airQuality: AirQuality?
){
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
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                        Text(
                            text = "${(weatherData?.current?.tempC?.toInt())}°C",
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(
                            text = "${weatherData?.current?.condition?.text}",
                            color = Color.White
                        )
                    Button(
                        onClick = { navController.navigate("details") },
                        modifier = Modifier.padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray
                        )
                    ) {

                        Text(
                            text =
                            """ИКВ ${calculateOverallAQI(airQuality)}""",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
