package com.sahil.practicaltask.weather

import androidx.recyclerview.widget.RecyclerView
import com.sahil.practicaltask.databinding.ItemWeatherBinding
import com.sahil.practicaltask.models.WeatherResponse
import com.sahil.practicaltask.utils.Utility

class ItemViewHolder(private val binding: ItemWeatherBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(weatherResponse: WeatherResponse){

        weatherResponse.dt?.let {timeStamp ->
            binding.tvDt.text = Utility.timeStamp2Time(timeStamp)
        }

        weatherResponse.temp?.let { temp ->
            binding.tvTemp.text = (temp.toDouble().toInt()/10).toString().plus(" °C")

        }

        binding.tvWind.text = weatherResponse.windSpeed.plus(" Km/h")
        binding.tvHumidity.text = weatherResponse.humidity.plus( " %")
    }
}