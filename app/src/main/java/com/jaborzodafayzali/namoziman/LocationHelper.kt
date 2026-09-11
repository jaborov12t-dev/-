package com.jaborzodafayzali.namoziman

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

data class UserLocation(
    val latitude: Double,
    val longitude: Double
)

class LocationHelper(private val context: Context) {

    private val client =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback? = null

    fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        onResult: (UserLocation?) -> Unit
    ) {
        if (!hasPermission()) {
            onResult(null)
            return
        }

        val request = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdateAgeMillis(10_000)
            .setDurationMillis(15_000)
            .build()

        client.getCurrentLocation(
            request,
            com.google.android.gms.tasks.CancellationTokenSource().token
        ).addOnSuccessListener { location ->

            if (location != null) {
                onResult(
                    UserLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
            } else {
                getLastLocation(onResult)
            }

        }.addOnFailureListener {
            getLastLocation(onResult)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(
        onResult: (UserLocation?) -> Unit
    ) {
        client.lastLocation
            .addOnSuccessListener { location ->

                if (location != null) {
                    onResult(
                        UserLocation(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                } else {
                    onResult(null)
                }

            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(
        onLocationChanged: (UserLocation) -> Unit
    ) {
        if (!hasPermission()) return

        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            30_000L
        )
            .setMinUpdateIntervalMillis(10_000L)
            .setWaitForAccurateLocation(false)
            .build()

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(
                result: LocationResult
            ) {
                val location = result.lastLocation ?: return

                onLocationChanged(
                    UserLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
            }
        }

        client.requestLocationUpdates(
            request,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        locationCallback?.let {
            client.removeLocationUpdates(it)
        }

        locationCallback = null
    }
}
