package com.example.WeatherForSpecificDays

import com.google.gson.annotations.SerializedName


data class Day (

  @SerializedName("maxtemp_c"      ) var maxtempC      : Double?          = null,
  @SerializedName("maxtemp_f"      ) var maxtempF      : Double?          = null,
  @SerializedName("mintemp_c"      ) var mintempC      : Double?          = null,
  @SerializedName("mintemp_f"      ) var mintempF      : Double?          = null,
  @SerializedName("avgtemp_c"      ) var avgtempC      : Double?          = null,
  @SerializedName("avgtemp_f"      ) var avgtempF      : Double?          = null,
  @SerializedName("maxwind_mph"    ) var maxwindMph    : Float?             = null,
  @SerializedName("maxwind_kph"    ) var maxwindKph    : Double?          = null,
  @SerializedName("totalprecip_mm" ) var totalprecipMm : Double?          = null,
  @SerializedName("totalprecip_in" ) var totalprecipIn : Double?          = null,
  @SerializedName("totalsnow_cm"   ) var totalsnowCm   : Int?             = null,
  @SerializedName("avgvis_km"      ) var avgvisKm      : Double?          = null,
  @SerializedName("avgvis_miles"   ) var avgvisMiles   : Int?             = null,
  @SerializedName("avghumidity"    ) var avghumidity   : Int?             = null,
  @SerializedName("tides"          ) var tides         : ArrayList<Tides> = arrayListOf(),
  @SerializedName("condition"      ) var condition     : Condition?       = Condition(),
  @SerializedName("uv"             ) var uv            : Int?             = null

)