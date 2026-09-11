package com.jaborzodafayzali.namoziman

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

            if (location == null) return@getLocation

            val timezone =
                TimeZone.getDefault().rawOffset / 3600000.0

            prayerTimes =
                PrayerTimesCalculator.calculate(
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
    val gray = Color(0xFFB8C5C1)

    var currentScreen by remember {
        mutableStateOf(AppScreen.HOME)
    }

    val location = MainActivity.appLocation
    val times = MainActivity.prayerTimes

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = dark
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {

                    when (currentScreen) {

                        AppScreen.HOME -> {
                            HomeScreen(
                                location = location,
                                times = times,
                                green = green,
                                gold = gold,
                                gray = gray
                            )
                        }

                        AppScreen.NAMOZ -> {
                            NamozScreen(
                                times = times,
                                green = green,
                                gold = gold
                            )
                        }

                        AppScreen.QURAN -> {
                            QuranScreen(
                                green = green,
                                gold = gold
                            )
                        }

                        AppScreen.DUAS -> {
                            DuasScreen(
                                green = green,
                                gold = gold
                            )
                        }

                        AppScreen.QIBLA -> {
                            QiblaScreen(
                                location = location,
                                green = green,
                                gold = gold
                            )
                        }
                    }
                }

                BottomNavigationBar(
                    currentScreen = currentScreen,
                    onScreenSelected = {
                        currentScreen = it
                    },
                    green = green,
                    gold = gold
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    location: AppLocation,
    times: PrayerTimes,
    green: Color,
    gold: Color,
    gray: Color
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(modifier = Modifier.height(28.dp))

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
                text = "${location.city}, ${location.country} 📍",
                color = gray,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

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
                        color = gray,
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Имрӯз",
                        color = gold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Вақти намоз",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        item {
            PrayerTime("Бомдод", times.fajr, gold)
            PrayerTime("Пешин", times.dhuhr, gold)
            PrayerTime("Аср", times.asr, gold)
            PrayerTime("Шом", times.maghrib, gold)
            PrayerTime("Хуфтан", times.isha, gold)

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Text(
                text = "Хидматҳо",
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HomeFeatureCard(
                    "🕌",
                    "Намоз",
                    Modifier.weight(1f),
                    green,
                    gold
                )

                HomeFeatureCard(
                    "📖",
                    "Қуръон",
                    Modifier.weight(1f),
                    green,
                    gold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HomeFeatureCard(
                    "🤲",
                    "Дуоҳо",
                    Modifier.weight(1f),
                    green,
                    gold
                )

                HomeFeatureCard(
                    "🧭",
                    "Қибла",
                    Modifier.weight(1f),
                    green,
                    gold
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

@Composable
fun NamozScreen(
    times: PrayerTimes,
    green: Color,
    gold: Color
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "Намоз 🕌",
                color = gold,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Вақтҳои намоз ва маълумот",
                color = Color.LightGray,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            PrayerLargeCard(
                "Бомдод",
                "05:12",
                "2 суннат + 2 фарз",
                times.fajr,
                green,
                gold
            )

            PrayerLargeCard(
                "Пешин",
                "12:40",
                "4 суннат + 4 фарз + 2 суннат",
                times.dhuhr,
                green,
                gold
            )

            PrayerLargeCard(
                "Аср",
                "16:25",
                "4 суннат + 4 фарз",
                times.asr,
                green,
                gold
            )

            PrayerLargeCard(
                "Шом",
                "18:42",
                "3 фарз + 2 суннат",
                times.maghrib,
                green,
                gold
            )

            PrayerLargeCard(
                "Хуфтан",
                "20:10",
                "4 фарз + 2 суннат + 3 витр",
                times.isha,
                green,
                gold
            )
        }
    }
}

@Composable
fun QuranScreen(
    green: Color,
    gold: Color
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "Қуръон 📖",
                color = gold,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Сураҳои Қуръони Карим",
                color = Color.LightGray,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(18.dp))
        }

        items(
            listOf(
                "1. Ал-Фотиҳа",
                "2. Ал-Бақара",
                "3. Оли Имрон",
                "4. Ан-Нисо",
                "5. Ал-Моида",
                "6. Ал-Анъом",
                "7. Ал-Аъроф",
                "8. Ал-Анфол",
                "9. Ат-Тавба",
                "10. Юнус"
            )
        ) { surah ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = green
                )
            ) {

                Text(
                    text = surah,
                    modifier = Modifier.padding(18.dp),
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Composable
fun DuasScreen(
    green: Color,
    gold: Color
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "Дуоҳо 🤲",
                color = gold,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))
        }

        items(
            listOf(
                "Дуои саҳар",
                "Дуо пеш аз хоб",
                "Дуо пеш аз хӯрок",
                "Дуо баъд аз хӯрок",
                "Дуо барои падару модар",
                "Дуои сафар",
                "Дуои истиғфор",
                "Дуои муҳофизат"
            )
        ) { dua ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = green
                )
            ) {

                Text(
                    text = dua,
                    modifier = Modifier.padding(18.dp),
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Composable
fun QiblaScreen(
    location: AppLocation,
    green: Color,
    gold: Color
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Қибла 🧭",
            color = gold,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
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
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🕋",
                    fontSize = 70.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Самти Қибла",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Барои муайян кардани самти дақиқи Қибла компаси телефон истифода мешавад.",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "${location.city}, ${location.country}",
                    color = gold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit,
    green: Color,
    gold: Color
) {

    NavigationBar(
        containerColor = Color(0xFF061611)
    ) {

        AppScreen.values().forEach { screen ->

            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = {
                    onScreenSelected(screen)
                },
                icon = {
                    Text(
                        text = screen.icon,
                        fontSize = 20.sp
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = gold,
                    selectedTextColor = gold,
                    indicatorColor = green,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray
                )
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
        horizontalArrangement = Arrangement.SpaceBetween
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
fun PrayerLargeCard(
    name: String,
    exampleTime: String,
    rakats: String,
    actualTime: String,
    green: Color,
    gold: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = green
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = actualTime,
                    color = gold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = rakats,
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun HomeFeatureCard(
    icon: String,
    title: String,
    modifier: Modifier,
    green: Color,
    gold: Color
) {

    Card(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = green
        )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = icon,
                fontSize = 25.sp
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
