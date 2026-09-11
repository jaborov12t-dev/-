package com.jaborzodafayzali.namoziman

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object QiblaHelper {

    private const val KAABA_LATITUDE = 21.4225
    private const val KAABA_LONGITUDE = 39.8262

    fun calculateQiblaBearing(
        latitude: Double,
        longitude: Double
    ): Float {

        val userLat = Math.toRadians(latitude)
        val userLon = Math.toRadians(longitude)

        val kaabaLat = Math.toRadians(KAABA_LATITUDE)
        val kaabaLon = Math.toRadians(KAABA_LONGITUDE)

        val deltaLon = kaabaLon - userLon

        val y = sin(deltaLon)

        val x =
            cos(userLat) * sin(kaabaLat) -
                    sin(userLat) *
                    cos(kaabaLat) *
                    cos(deltaLon)

        var bearing =
            Math.toDegrees(
                atan2(y, x)
            )

        bearing = (bearing + 360.0) % 360.0

        return bearing.toFloat()
    }

    fun directionName(
        bearing: Float
    ): String {

        return when {

            bearing < 22.5 || bearing >= 337.5 ->
                "Шимол"

            bearing < 67.5 ->
                "Шимолу шарқ"

            bearing < 112.5 ->
                "Шарқ"

            bearing < 157.5 ->
                "Ҷанубу шарқ"

            bearing < 202.5 ->
                "Ҷануб"

            bearing < 247.5 ->
                "Ҷанубу ғарб"

            bearing < 292.5 ->
                "Ғарб"

            else ->
                "Шимолу ғарб"
        }
    }
}
