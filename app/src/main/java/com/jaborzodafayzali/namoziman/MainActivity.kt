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

class MainActivity : ComponentActivity() {

    private val locationPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermission.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        setContent {
            NamozimanApp()
        }
    }
}

@Composable
fun NamozimanApp() {

    val green = Color(0xFF0B3D32)
    val dark = Color(0xFF071C18)
    val gold = Color(0xFFD4AF37)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
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
                    text = "Душанбе, Тоҷикистон 🇹🇯",
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
                            text = "Намози навбатӣ",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "ШОМ",
                            color = gold,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "18:42",
                            color = Color.White,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "То намози навбатӣ: 01:24:35",
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                PrayerTime("Бомдод", "05:12")
                PrayerTime("Пешин", "12:40")
                PrayerTime("Аср", "16:25")
                PrayerTime("Шом", "18:42")
                PrayerTime("Хуфтан", "20:10")

                Spacer(modifier = Modifier.height(20.dp))

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
fun PrayerTime(name: String, time: String) {
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
fun FeatureButton(icon: String, title: String) {
    Button(
        onClick = { },
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
