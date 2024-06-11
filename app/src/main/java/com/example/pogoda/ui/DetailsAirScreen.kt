package com.example.pogoda.ui
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pogoda.Models.WeatherRealTime.AirQuality
import kotlin.reflect.full.memberProperties
@Composable
fun AirDetailsScreen(
    weatherViewModel: WeatherViewModel,
    navControler: NavController,
    airQuality: AirQuality?
) {
    val weatherData = weatherViewModel.weatherData
    val isLoading = weatherViewModel.isLoading
    val errorMessage = weatherViewModel.errorMessage

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = { BackTopBar(navControler) },
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        all = 16.dp
                    )
            ) {
                when {
                    isLoading -> {
                        Text(text = "Loading...", color = Color.White)
                    }
                    errorMessage != null -> {
                        Text(text = "Error: $errorMessage", color = Color.Red)
                    }
                    weatherData != null -> {
                        airQuality?.let { airQuality ->
                            Text(
                                text = "${calculateOverallAQI(airQuality)}",
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.displayLarge
                            )
                            PrintAirQuality(airQuality)
                        } ?: Text(
                            text = "Air quality is not available",
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}

fun calculateAQI(concentration: Double, breakpoints: List<Pair<Double, Double>>, indexRanges: List<Pair<Int, Int>>): Int {
    for (i in breakpoints.indices) {
        val (C_low, C_high) = breakpoints[i]
        if (concentration in C_low..C_high) {
            val (I_low, I_high) = indexRanges[i]
            return ((I_high - I_low) / (C_high - C_low) * (concentration - C_low) + I_low).toInt()
        }
    }
    return -1 // Возвращаем -1, если концентрация вне диапазона
}
fun calculateOverallAQI(airQuality: AirQuality?): Int {
    val breakpointsPM25 = listOf(0.0 to 12.0, 12.1 to 35.4, 35.5 to 55.4, 55.5 to 150.4, 150.5 to 250.4, 250.5 to 350.4, 350.5 to 500.4)
    val indexRangesPM25 = listOf(0 to 50, 51 to 100, 101 to 150, 151 to 200, 201 to 300, 301 to 400, 401 to 500)

    val breakpointsPM10 = listOf(0.0 to 54.0, 55.0 to 154.0, 155.0 to 254.0, 255.0 to 354.0, 355.0 to 424.0, 425.0 to 504.0, 505.0 to 604.0)
    val indexRangesPM10 = listOf(0 to 50, 51 to 100, 101 to 150, 151 to 200, 201 to 300, 301 to 400, 401 to 500)

    val breakpointsO3 = listOf(0.0 to 0.054, 0.055 to 0.070, 0.071 to 0.085, 0.086 to 0.105, 0.106 to 0.200)
    val indexRangesO3 = listOf(0 to 50, 51 to 100, 101 to 150, 151 to 200, 201 to 300)

    val breakpointsNO2 = listOf(0.0 to 0.053, 0.054 to 0.100, 0.101 to 0.360, 0.361 to 0.649, 0.650 to 1.249, 1.250 to 1.649, 1.650 to 2.049)
    val indexRangesNO2 = listOf(0 to 50, 51 to 100, 101 to 150, 151 to 200, 201 to 300, 301 to 400, 401 to 500)

    val breakpointsSO2 = listOf(0.0 to 0.035, 0.036 to 0.075, 0.076 to 0.185, 0.186 to 0.304, 0.305 to 0.604, 0.605 to 0.804, 0.805 to 1.004)
    val indexRangesSO2 = listOf(0 to 50, 51 to 100, 101 to 150, 151 to 200, 201 to 300, 301 to 400, 401 to 500)

    val breakpointsCO = listOf(0.0 to 4.4, 4.5 to 9.4, 9.5 to 12.4, 12.5 to 15.4, 15.5 to 30.4, 30.5 to 40.4, 40.5 to 50.4)
    val indexRangesCO = listOf(0 to 50, 51 to 100, 101 to 150, 151 to 200, 201 to 300, 301 to 400, 401 to 500)

    val aqiList = mutableListOf<Int>()

    airQuality!!.pm25?.let { aqiList.add(calculateAQI(it, breakpointsPM25, indexRangesPM25)) }
    airQuality.pm10?.let { aqiList.add(calculateAQI(it, breakpointsPM10, indexRangesPM10)) }
    airQuality.o3?.let { aqiList.add(calculateAQI(it, breakpointsO3, indexRangesO3)) }
    airQuality.no2?.let { aqiList.add(calculateAQI(it, breakpointsNO2, indexRangesNO2)) }
    airQuality.so2?.let { aqiList.add(calculateAQI(it, breakpointsSO2, indexRangesSO2)) }
    airQuality.co?.let { aqiList.add(calculateAQI(it, breakpointsCO, indexRangesCO)) }

    Log.i("Otladka2", "${aqiList.joinToString(",")}")
    return aqiList.maxOrNull() ?: -1
}

@Composable
fun PrintAirQuality(airQuality: AirQuality){
    Box(modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            FastParsingAirQuality(airQuality)
        }
    }
}

@Composable
fun FastParsingAirQuality(airQuality: AirQuality) {
    AirQuality::class.memberProperties.forEach { property ->
        val name = property.name
        val value = property.get(airQuality)
        Log.i("Otladka1", "${name} ${value}")
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(navControler: NavController) {
    androidx.compose.material3.TopAppBar(
        modifier = Modifier.height(56.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,
            titleContentColor = Color.White
        ),
        title = {"Заглушка"},
        navigationIcon = {
            IconButton(onClick = { navControler.navigate("main") }) {
                Icon(Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.DarkGray
                )
            }
        },
    )
}
