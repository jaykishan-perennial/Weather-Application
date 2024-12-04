package com.example.openweatherapp.ui.dashboard.weather_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.openweatherapp.databinding.FragmentWeatherHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherHistoryFragment : Fragment() {

    private lateinit var binding: FragmentWeatherHistoryBinding
    private val weatherHistoryViewModel: WeatherHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherHistoryViewModel.getWeatherHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        weatherHistoryViewModel.weatherHistory.observe(viewLifecycleOwner) { history ->
            binding.tvEmptyList.isVisible = history.isEmpty()
            binding.rvWeatherHistory.isVisible = history.isNotEmpty()
            if (history.isNotEmpty()){
                binding.rvWeatherHistory.adapter = WeatherHistoryAdapter(history)
            }
        }
    }

}