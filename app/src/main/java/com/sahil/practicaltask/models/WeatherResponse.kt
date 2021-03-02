package com.sahil.practicaltask.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse (

    @field:Json(name = "dt")
    val dt: String? = null,

    @field:Json(name = "temp")
    val temp: String? = null,

    @field:Json(name = "humidity")
    val humidity: String? = null,

    @field:Json(name = "clouds")
    val cloud: String? = null,

    @field:Json(name = "wind_speed")
    val windSpeed: String? = null,

    @field:Json(name = "wind_deg")
    val windDeg: String? = null


)