package com.example.openweatherapp.utils.utility

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationHelper @Inject constructor(
    @ApplicationContext val context: Context
) {
    val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun isLocationEnabled(): Boolean {
        return hasGPSEnabled() || hasNetworkEnabled()
    }

    private fun hasGPSEnabled() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun hasNetworkEnabled() = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates() = callbackFlow<Location?>{
        val listener = object : android.location.LocationListener{
            override fun onLocationChanged(location: Location) {
                trySend(location)
            }
        }

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            5000,
            0F,
            listener
        )
        awaitClose {
            locationManager.removeUpdates(listener)
        }
    }
}