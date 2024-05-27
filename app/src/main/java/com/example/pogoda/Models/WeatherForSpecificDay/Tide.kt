package com.example.WeatherForSpecificDays

import com.google.gson.annotations.SerializedName


data class Tide (

  @SerializedName("tide_time"      ) var tideTime     : String? = null,
  @SerializedName("tide_height_mt" ) var tideHeightMt : String? = null,
  @SerializedName("tide_type"      ) var tideType     : String? = null

)