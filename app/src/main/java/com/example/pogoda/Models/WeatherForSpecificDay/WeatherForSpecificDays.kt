package com.example.WeatherForSpecificDays

import com.google.gson.annotations.SerializedName


data class WeatherForSpecificDays (

  @SerializedName("location" ) var location : Location? = Location(),
  @SerializedName("forecast" ) var forecast : Forecast? = Forecast()

)