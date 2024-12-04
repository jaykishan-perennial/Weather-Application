package com.example.openweatherapp.ui.dashboard.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.openweatherapp.R
import com.example.openweatherapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    private var locationPermissionRequest : ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            onPermissionResult(isGranted)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.offlineLayout.btnRetry.setOnClickListener {
            homeViewModel.onRetry()
        }
        binding.btnGrant.setOnClickListener { v ->
            openAppDetailsScreen()
        }
        locationPermissionRequest?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onResume() {
        super.onResume()
        if (hasLocationPermission()) {
            binding.noPermissionsLayout.isVisible = false
            if (homeViewModel.locationServiceEnabled()) {
                getWeatherData()
            } else {
                binding.locationDisabledLayout.isVisible = true
                listenForLocationState()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationPermissionRequest = null
    }

    private fun getWeatherData() {
        lifecycleScope.launch {
            homeViewModel.getWeatherInfo()
        }
    }

    private fun listenForLocationState() {
        lifecycleScope.launch {
            while (true){
                val locationServicesEnabled = homeViewModel.locationServiceEnabled().also {
                    binding.locationDisabledLayout.isVisible = it.not()
                }
                if (locationServicesEnabled && homeViewModel.isNetworkAvailable()) {
                    getWeatherData()
                    cancel()
                }
                delay(1000)
            }
        }
    }

    private fun onPermissionResult(isGranted: Boolean) {
        if (isGranted.not()) {
            if (requireActivity().shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.title_permission_required))
                    setMessage(getString(R.string.msg_permission_required))
                    setPositiveButton(getString(R.string.action_grant)) { _, _ ->
                        openAppDetailsScreen()
                    }
                    setNegativeButton(getString(R.string.action_cancel)) { di, _ ->
                        di.dismiss()
                        requireActivity().finish()
                    }
                    show()
                }
            } else {
                binding.apply {
                    noPermissionsLayout.isVisible = true
                }
            }
            return
        }
        if (homeViewModel.locationServiceEnabled()) {
            getWeatherData()
        } else {
            binding.locationDisabledLayout.isVisible = true
            listenForLocationState()
        }
    }

    private fun openAppDetailsScreen() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        requireActivity().startActivity(intent)
    }

    private fun hasLocationPermission(): Boolean {
        return requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

}