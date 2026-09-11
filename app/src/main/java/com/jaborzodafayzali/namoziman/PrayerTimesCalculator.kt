package com.jaborzodafayzali.namoziman

import java.util.Calendar
import kotlin.math.*

data class PrayerTimes(
    val fajr: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)

object PrayerTimesCalculator {

    fun calculate(
        latitude: Double,
        longitude: Double,
        timezone: Double,
        date: Calendar = Calendar.getInstance()
    ): PrayerTimes {

        val day = date.get(Calendar.DAY_OF_YEAR)

        val declination = 23.45 *
                sin(Math.toRadians(360.0 * (284 + day) / 365.0))

        val solarNoon = 12.0 - longitude / 15.0 + timezone

        val sunrise = solarTime(
            -0.833,
            latitude,
            declination,
            solarNoon,
            true
        )

        val sunset = solarTime(
            -0.833,
            latitude,
            declination,
            solarNoon,
            false
        )

        val fajr = solarTime(
            -18.0,
            latitude,
            declination,
            solarNoon,
            true
        )

        val isha = solarTime(
            -18.0,
            latitude,
            declination,
            solarNoon,
            false
        )

        val dhuhr = solarNoon
        val asr = asrTime(latitude, declination, dhuhr)

        return PrayerTimes(
            fajr = formatTime(fajr),
            dhuhr = formatTime(dhuhr),
            asr = formatTime(asr),
            maghrib = formatTime(sunset),
            isha = formatTime(isha)
        )
    }

    private fun solarTime(
        angle: Double,
        latitude: Double,
        declination: Double,
        solarNoon: Double,
        morning: Boolean
    ): Double {

        val latRad = Math.toRadians(latitude)
        val decRad = Math.toRadians(declination)

        val cosHour =
            (sin(Math.toRadians(angle)) -
                    sin(latRad) * sin(decRad)) /
                    (cos(latRad) * cos(decRad))

        val hourAngle =
            Math.toDegrees(acos(cosHour)) / 15.0

        return if (morning) {
            solarNoon - hourAngle
        } else {
            solarNoon + hourAngle
        }
    }

    private fun asrTime(
        latitude: Double,
        declination: Double,
        dhuhr: Double
    ): Double {

        val shadowAngle = 1.0

        val latRad = Math.toRadians(latitude)
        val decRad = Math.toRadians(declination)

        val angle = Math.toDegrees(
            atan(
                1.0 /
                        (shadowAngle +
                                tan(abs(latRad - decRad)))
            )
        )

        val cosHour =
            (sin(Math.toRadians(angle)) -
                    sin(latRad) * sin(decRad)) /
                    (cos(latRad) * cos(decRad))

        val hourAngle =
            Math.toDegrees(acos(cosHour)) / 15.0

        return dhuhr + hourAngle
    }

    private fun formatTime(time: Double): String {

        var hours = floor(time).toInt()
        var minutes = ((time - hours) * 60).roundToInt()

        if (minutes >= 60) {
            hours++
            minutes -= 60
        }

        hours %= 24

        return "%02d:%02d".format(hours, minutes)
    }
}
