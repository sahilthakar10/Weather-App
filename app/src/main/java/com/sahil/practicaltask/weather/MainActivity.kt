package com.sahil.practicaltask.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationServices
import com.sahil.practicaltask.databinding.ActivityMainBinding
import com.sahil.practicaltask.extensions.getViewModel
import com.sahil.practicaltask.models.ResponseData
import com.sahil.practicaltask.utils.StringConstants
import com.sahil.practicaltask.utils.Utility
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.helper.PermissionHelper

class MainActivity : AppCompatActivity(), ConnectionCallbacks,
    OnConnectionFailedListener {

    var mGoogleApiClient: GoogleApiClient? = null
    private var weatherViewModel: WeatherViewModel? = null
    private var mLastLocation: Location? = null
    private lateinit var binding: ActivityMainBinding
    private var weatherAdapter: WeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherViewModel = getViewModel()

        checkLocationPermission()
        setWeatherList()
        setGoogleAppClient()
        setObserver()

    }

    private fun setGoogleAppClient() {
        mGoogleApiClient =
            GoogleApiClient.Builder(applicationContext).addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
        mGoogleApiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLastLocation =  LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
                weatherViewModel?.getWeatherDetails(mLastLocation?.latitude.toString(), mLastLocation?.longitude.toString(),
                    StringConstants.exclude, StringConstants.AppId)
            }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    private fun setObserver(){

        weatherViewModel?.showLoader?.observe(this, Observer {isShow ->
            handleLoader(isShow)
        })

        weatherViewModel?.getWeatherDetails?.observe(this, Observer { responseData ->

            setData(responseData)

        })

    }

    private fun handleLoader(isShow: Boolean){

        if (isShow){
            Utility.showViews(binding.pbWeatherDetails, binding.vWrapper)
            Utility.hideViews(binding.cardView)
        }else{
            Utility.hideViews(binding.pbWeatherDetails, binding.vFirstTimeWrapper, binding.vWrapper)
            Utility.showViews(binding.cardView)
        }

    }

    private fun setWeatherList(){

        weatherAdapter = WeatherAdapter()
        binding.rvWeather.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
            adapter = weatherAdapter
        }

    }

    private fun setData(responseData: ResponseData){
        responseData.current?.dt?.let {timeStamp ->
            binding.tvDate.text = Utility.timeStamp2DateTime(timeStamp)
        }
        binding.tvHumidityV.text = responseData.current?.humidity.plus(" %")
        binding.tvWindV.text = responseData.current?.windSpeed?.plus( " Km/h")

        responseData.current?.temp?.let { temp ->
            binding.tvTemp.text = (temp.toDouble().toInt()/10).toString().plus(" °C")
        }

        responseData.hourly?.let { hourly ->
            weatherAdapter?.setData(ArrayList(hourly))
        }

    }

    @SuppressLint("RestrictedApi")
    private fun checkLocationPermission() {
        if (!EasyPermissions.hasPermissions(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            PermissionHelper.newInstance(this).directRequestPermissions(
                1,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) setGoogleAppClient()
    }


}