package com.sahil.practicaltask.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sahil.practicaltask.extensions.apiRx
import com.sahil.practicaltask.models.ResponseData
import com.sahil.practicaltask.network.ApiRepository
import java.lang.Exception

class WeatherViewModel (application: Application) : AndroidViewModel(application) {

    private val context = application
    private val _showLoader = MutableLiveData<Boolean>()
    private val _getWeatherDetails = MutableLiveData<ResponseData>()

    val showLoader: LiveData<Boolean> = _showLoader
    val getWeatherDetails: LiveData<ResponseData> = _getWeatherDetails
    val TAG = "WeatherViewModel"

    fun getWeatherDetails(lat: String, lang: String, exclude: String, appId: String){

        try {
            _showLoader.postValue(true)

            apiRx<ResponseData> {
                request = ApiRepository.getWeatherData(lat, lang, exclude, appId)

                success { weatherDataResponse ->

                    _showLoader.postValue(false)
                    _getWeatherDetails.postValue(weatherDataResponse)
                    Log.d(TAG, weatherDataResponse.toString())
                }

                error { e ->
                    Log.d(TAG, "👺" + e.message)
                }
            }

        }catch (e: Exception){
            Log.d(TAG, e.message.toString())
        }

    }

}