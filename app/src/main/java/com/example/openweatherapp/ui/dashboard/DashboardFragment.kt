package com.example.openweatherapp.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.openweatherapp.R
import com.example.openweatherapp.databinding.FragmentDashboardBinding
import com.example.openweatherapp.ui.dashboard.home.HomeFragment
import com.example.openweatherapp.ui.dashboard.weather_history.WeatherHistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    changeFragment(HomeFragment())
                }
                R.id.item_history -> {
                    changeFragment(WeatherHistoryFragment())
                }
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.item_home
    }

    private fun changeFragment(
        fragment: Fragment
    ) {
        childFragmentManager.popBackStack()
        childFragmentManager
            .beginTransaction()
            .replace(binding.flDashboard.id, fragment)
            .commit()
    }

}

