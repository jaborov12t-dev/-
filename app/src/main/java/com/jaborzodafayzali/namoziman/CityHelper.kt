package com.jaborzodafayzali.namoziman

import android.content.Context
import android.location.Geocoder
import java.util.Locale

data class CityInfo(
    val city: String,
    val country: String
)

object CityHelper {

    fun getCity(
        context: Context,
        latitude: Double,
        longitude: Double
    ): CityInfo {

        return try {

            val geocoder = Geocoder(
                context,
                Locale("tg")
            )

            val addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            )

            if (!addresses.isNullOrEmpty()) {

                val address = addresses[0]

                val city =
                    address.locality
                        ?: address.subAdminArea
                        ?: address.adminArea
                        ?: "Номаълум"

                val country =
                    address.countryName
                        ?: "Номаълум"

                CityInfo(
                    city = city,
                    country = country
                )

            } else {

                CityInfo(
                    city = "Номаълум",
                    country = "Номаълум"
                )
            }

        } catch (e: Exception) {

            CityInfo(
                city = "Номаълум",
                country = "Номаълум"
            )
        }
    }
}
