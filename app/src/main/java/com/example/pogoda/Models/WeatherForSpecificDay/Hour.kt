package com.example.WeatherForSpecificDays

import com.google.gson.annotations.SerializedName


data class Hour (

  @SerializedName("time_epoch"         ) var timeEpoch       : Int?       = null,
  @SerializedName("time"               ) var time            : String?    = null,
  @SerializedName("temp_c"             ) var tempC           : Double?    = null,
  @SerializedName("temp_f"             ) var tempF           : Double?    = null,
  @SerializedName("is_day"             ) var isDay           : Int?       = null,
  @SerializedName("condition"          ) var condition       : Condition? = Condition(),
  @SerializedName("wind_mph"           ) var windMph         : Double?    = null,
  @SerializedName("wind_kph"           ) var windKph         : Double?    = null,
  @SerializedName("wind_degree"        ) var windDegree      : Int?       = null,
  @SerializedName("wind_dir"           ) var windDir         : String?    = null,
  @SerializedName("pressure_mb"        ) var pressureMb      : Int?       = null,
  @SerializedName("pressure_in"        ) var pressureIn      : Double?    = null,
  @SerializedName("precip_mm"          ) var precipMm        : Float?       = null,
  @SerializedName("precip_in"          ) var precipIn        : Float?       = null,
  @SerializedName("humidity"           ) var humidity        : Int?       = null,
  @SerializedName("cloud"              ) var cloud           : Int?       = null,
  @SerializedName("feelslike_c"        ) var feelslikeC      : Double?    = null,
  @SerializedName("feelslike_f"        ) var feelslikeF      : Double?    = null,
  @SerializedName("windchill_c"        ) var windchillC      : Double?    = null,
  @SerializedName("windchill_f"        ) var windchillF      : Double?    = null,
  @SerializedName("heatindex_c"        ) var heatindexC      : Double?    = null,
  @SerializedName("heatindex_f"        ) var heatindexF      : Double?    = null,
  @SerializedName("dewpoint_c"         ) var dewpointC       : Double?    = null,
  @SerializedName("dewpoint_f"         ) var dewpointF       : Double?    = null,
  @SerializedName("vis_km"             ) var visKm           : Int?       = null,
  @SerializedName("vis_miles"          ) var visMiles        : Int?       = null,
  @SerializedName("gust_mph"           ) var gustMph         : Double?    = null,
  @SerializedName("gust_kph"           ) var gustKph         : Double?    = null,
  @SerializedName("uv"                 ) var uv              : Int?       = null,
  @SerializedName("sig_ht_mt"          ) var sigHtMt         : Float?       = null,
  @SerializedName("swell_ht_mt"        ) var swellHtMt       : Double?    = null,
  @SerializedName("swell_ht_ft"        ) var swellHtFt       : Double?    = null,
  @SerializedName("swell_dir"          ) var swellDir        : Int?       = null,
  @SerializedName("swell_dir_16_point" ) var swellDir16Point : String?    = null,
  @SerializedName("swell_period_secs"  ) var swellPeriodSecs : Double?    = null,
  @SerializedName("water_temp_c"       ) var waterTempC      : Float?       = null,
  @SerializedName("water_temp_f"       ) var waterTempF      : Double?    = null

)