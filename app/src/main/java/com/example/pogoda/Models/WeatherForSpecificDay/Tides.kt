package com.example.WeatherForSpecificDays

import com.google.gson.annotations.SerializedName


data class Tides (

  @SerializedName("tide" ) var tide : ArrayList<Tide> = arrayListOf()

)