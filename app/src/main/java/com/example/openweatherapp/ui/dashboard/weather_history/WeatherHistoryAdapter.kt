package com.example.openweatherapp.ui.dashboard.weather_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openweatherapp.data.source.remote.WeatherInfo
import com.example.openweatherapp.databinding.ItemWeatherBinding

class WeatherHistoryAdapter(
    private val weatherHistory: List<WeatherInfo>
) : RecyclerView.Adapter<WeatherHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = weatherHistory[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return weatherHistory.size
    }

    inner class ViewHolder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(weatherInfo: WeatherInfo) {
            binding.weatherInfo = weatherInfo
        }
    }
}