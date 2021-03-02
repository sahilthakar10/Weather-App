package com.sahil.practicaltask.network

import com.sahil.practicaltask.models.ResponseData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/onecall")
    fun getWeatherResponse(
        @Query("lat") q: String,
        @Query("lon") units: String,
        @Query("exclude") lang: String,
        @Query("appid") appId: String
    ): Single<ResponseData>


}