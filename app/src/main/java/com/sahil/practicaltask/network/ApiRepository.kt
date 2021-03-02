package com.sahil.practicaltask.network

import android.util.Log
import com.sahil.practicaltask.models.ResponseData
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiRepository {

    private val apiService: ApiService
    const val TAG = "ApiRepository"

    init {

        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d(TAG, "📶 $message")
            }
        })

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        okHttpClient.addNetworkInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(Env.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient.build())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getWeatherData(lat: String, lang: String, exclude: String, appId: String): Single<ResponseData> {
        return apiService.getWeatherResponse(lat, lang, exclude, appId)
    }


}