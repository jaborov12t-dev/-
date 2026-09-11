package com.jaborzodafayzali.namoziman

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

data class UserLocation(
    val latitude: Double,
    val longitude: Double
)

class LocationHelper(private val context: Context) {

    private val client =
        LocationServices.getFusedLocationProviderClient(context)

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
    fun getLocation(
        onResult: (UserLocation?) -> Unit
    ) {
        if (!hasPermission()) {
            onResult(null)
            return
        }

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
}
