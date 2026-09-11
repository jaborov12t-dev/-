package com.jaborzodafayzali.namoziman

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import java.util.Calendar
import java.util.TimeZone

data class AppLocation(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

class MainActivity : ComponentActivity() {

    private lateinit var locationHelper: LocationHelper

    private val locationPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->

            val granted =
                result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                loadLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationHelper = LocationHelper(this)

        setContent {
            NamozimanApp()
        }

        if (hasLocationPermission()) {
            loadLocation()
        } else {
            locationPermission.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun loadLocation() {

        locationHelper.getLocation { location ->

            if (location == null) {
                return@getLocation
            }

            val timezone =
                TimeZone.getDefault().rawOffset / 3600000.0

            val times = PrayerTimesCalculator.calculate(
                latitude = location.latitude,
                longitude = location.longitude,
                timezone = timezone,
                date = Calendar.getInstance()
            )

            val city = CityHelper.getCity(
                this,
                location.latitude,
                location.longitude
            )

            appLocation = AppLocation(
                city = city.city,
                country = city.country,
                latitude = location.latitude,
                longitude = location.longitude
            )

            prayerTimes = times
        }
    }

    companion object {

        var prayerTimes by mutableStateOf(
            PrayerTimes(
                fajr = "--:--",
                dhuhr = "--:--",
                asr = "--:--",
                maghrib = "--:--",
                isha = "--:--"
            )
        )

        var appLocation by mutableStateOf(
            AppLocation(
                city = "Муайян карда мешавад",
                country = "",
                latitude = 0.0,
                longitude = 0.0
            )
        )
    }
}

@Composable
fun NamozimanApp() {

    val dark = Color(0xFF071C18)
    val green = Color(0xFF0B3D32)
    val gold = Color(0xFFD4AF37)
    val white = Color.White
    val gray = Color(0xFFB8C5C1)

    val times = MainActivity.prayerTimes
    val location = MainActivity.appLocation

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = dark
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Text(
                    text = "Намози Ман 🕌",
                    color = gold,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Ассалому алайкум 🤍",
                    color = white,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text =
                        if (location.country.isNotEmpty())
                            "${location.city}, ${location.country} 📍"
                        else
                            "${location.city} 📍",
                    color = gray,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                NextPrayerCard(
                    times = times,
                    green = green,
                    gold = gold
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                PrayerTime(
                    name = "Бомдод",
                    time = times.fajr,
                    gold = gold
                )

                PrayerTime(
                    name = "Пешин",
                    time = times.dhuhr,
                    gold = gold
                )

                PrayerTime(
                    name = "Аср",
                    time = times.asr,
                    gold = gold
                )

                PrayerTime(
                    name = "Шом",
                    time = times.maghrib,
                    gold = gold
                )

                PrayerTime(
                    name = "Хуфтан",
                    time = times.isha,
                    gold = gold
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    FeatureButton(
                        icon = "🕌",
                        title = "Намоз",
                        modifier = Modifier.weight(1f)
                    )

                    FeatureButton(
                        icon = "📖",
                        title = "Қуръон",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    FeatureButton(
                        icon = "🤲",
                        title = "Дуоҳо",
                        modifier = Modifier.weight(1f)
                    )

                    FeatureButton(
                        icon = "🧭",
                        title = "Қибла",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}

@Composable
fun NextPrayerCard(
    times: PrayerTimes,
    green: Color,
    gold: Color
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = green
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = "Намози навбатӣ",
                color = Color.LightGray,
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Имрӯз",
                color = gold,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Вақти намоз",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Вақтҳо мувофиқи ҷойгиршавии шумо ҳисоб мешаванд",
                color = Color.White,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun PrayerTime(
    name: String,
    time: String,
    gold: Color
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement =
            Arrangement.SpaceBetween,
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text = name,
            color = Color.White,
            fontSize = 17.sp
        )

        Text(
            text = time,
            color = gold,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FeatureButton(
