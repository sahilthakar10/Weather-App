package com.sahil.practicaltask.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sahil.practicaltask.databinding.ItemWeatherBinding
import com.sahil.practicaltask.models.WeatherResponse

class WeatherAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var mWeatherList: ArrayList<WeatherResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).onBind(mWeatherList[position])
    }

    override fun getItemCount(): Int {
        return mWeatherList.size
    }

    fun setData(weatherList: ArrayList<WeatherResponse>){
        mWeatherList.addAll(weatherList)
        notifyDataSetChanged()
    }

}
