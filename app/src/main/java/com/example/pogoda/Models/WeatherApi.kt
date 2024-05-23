package com.example.Models

import com.google.gson.annotations.SerializedName


data class WeatherApi (

  @SerializedName("location" ) var location : Location? = Location(),
  @SerializedName("current"  ) var current  : Current?  = Current()

)