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

class MainActivity : ComponentActivity() {

    private val locationPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { granted ->

            if (
                granted[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                granted[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                loadLocation()
            }
        }

    private var locationHelper: LocationHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationHelper = LocationHelper(this)

        setContent {
            NamozimanApp()
        }

        if (!hasLocationPermission()) {
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
        locationHelper?.getLocation { location ->

            if (location != null) {

                val timezone =
                    TimeZone.getDefault().rawOffset / 3600000.0

                val times = PrayerTimesCalculator.calculate(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    timezone = timezone,
                    date = Calendar.getInstance()
                )

                prayerTimes = times
            }
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
    }
}

@Composable
fun NamozimanApp() {

    val dark = Color(0xFF071C18)
    val green = Color(0xFF0B3D32)
    val gold = Color(0xFFD4AF37)

    val times = MainActivity.prayerTimes

    MaterialTheme {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(dark),
            color = dark
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Намози Ман 🕌",
                    color = gold,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ассалому алайкум 🤍",
                    color = Color.White,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Макон муайян карда мешавад 📍",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(25.dp))

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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Вақти намоз",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Имрӯз",
                            color = gold,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Вақтҳо аз рӯи ҷойгиршавии шумо ҳисоб мешаванд",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                PrayerTime("Бомдод", times.fajr)
                PrayerTime("Пешин", times.dhuhr)
                PrayerTime("Аср", times.asr)
                PrayerTime("Шом", times.maghrib)
                PrayerTime("Хуфтан", times.isha)

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureButton("🕌", "Намоз")
                    FeatureButton("📖", "Қуръон")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureButton("🤲", "Дуоҳо")
                    FeatureButton("🧭", "Қибла")
                }
            }
        }
    }
}

@Composable
fun PrayerTime(
    name: String,
    time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = name,
            color = Color.White,
            fontSize = 17.sp
        )

        Text(
            text = time,
            color = Color(0xFFD4AF37),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FeatureButton(
    icon: String,
    title: String
) {
    Button(
        onClick = {},
        modifier = Modifier
            .width(145.dp)
            .height(55.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "$icon $title",
            fontSize = 15.sp
        )
    }
}
