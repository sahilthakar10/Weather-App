package com.sahil.practicaltask.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseData (

    @field:Json(name = "current")
    val current: WeatherResponse? = null,

    @field:Json(name = "hourly")
    val hourly: List<WeatherResponse>? = null

)