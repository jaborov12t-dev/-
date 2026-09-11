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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import java.util.Calendar
import kotlin.math.max

private val DarkGreen = Color(0xFF071C18)
private val CardGreen = Color(0xFF102923)
private val Gold = Color(0xFFD6B56A)
private val White = Color(0xFFF5F3EA)
private val SoftWhite = Color(0xFFBFC8C3)

class MainActivity : ComponentActivity() {

    private lateinit var locationHelper: LocationHelper

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                loadLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationHelper = LocationHelper(this)

        setContent {
            NamozimanApp(
                requestLocation = {
                    requestLocationPermission()
                }
            )
        }

        if (locationHelper.hasPermission()) {
            loadLocation()
        }
    }

    private fun requestLocationPermission() {

        if (!locationHelper.hasPermission()) {

            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            loadLocation()
        }
    }

    private fun loadLocation() {

        locationHelper.getLocation { location ->

            if (location != null) {

                val city = CityHelper.getCity(
                    this,
                    location.latitude,
                    location.longitude
                )

                // Ҷойгиршавӣ баъдтар ба ҳолати асосии барнома
                // пайваст карда мешавад.
            }
        }
    }
}

@Composable
fun NamozimanApp(
    requestLocation: () -> Unit
) {

    var selectedScreen by remember {
        mutableStateOf(AppScreen.HOME)
    }

    var showNames by remember {
        mutableStateOf(false)
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Gold,
            background = DarkGreen,
            surface = CardGreen
        )
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkGreen
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    when {
                        showNames -> {

                            NamesOfAllahScreen(
                                onBack = {
                                    showNames = false
                                }
                            )
                        }

                        selectedScreen == AppScreen.HOME -> {

                            HomeScreen(
                                onNamoz = {
                                    selectedScreen = AppScreen.NAMOZ
                                },
                                onQuran = {
                                    selectedScreen = AppScreen.QURAN
                                },
                                onDuas = {
                                    selectedScreen = AppScreen.DUAS
                                },
                                onQibla = {
                                    selectedScreen = AppScreen.QIBLA
                                },
                                onNames = {
                                    showNames = true
                                },
                                onLocation = requestLocation
                            )
                        }

                        selectedScreen == AppScreen.NAMOZ -> {

                            NamozScreen()
                        }

                        selectedScreen == AppScreen.QURAN -> {

                            QuranScreen()
                        }

                        selectedScreen == AppScreen.DUAS -> {

                            DuasScreen()
                        }

                        selectedScreen == AppScreen.QIBLA -> {

                            QiblaScreen()
                        }
                    }
                }

                if (!showNames) {

                    BottomNavigationBar(
                        selectedScreen = selectedScreen,
                        onSelect = {
                            selectedScreen = it
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    onNamoz: () -> Unit,
    onQuran: () -> Unit,
    onDuas: () -> Unit,
    onQibla: () -> Unit,
    onNames: () -> Unit,
    onLocation: () -> Unit
) {

    val calendar = Calendar.getInstance()

    val prayerTimes = remember {

        PrayerTimesCalculator.calculate(
            latitude = 38.5598,
            longitude = 68.7870,
            timezone = 5.0,
            date = calendar
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {

            Text(
                text = "Ассалому алайкум 🤍",
                color = White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onLocation()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "📍 Душанбе, Тоҷикистон 🇹🇯",
                    color = SoftWhite,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Тағйир",
                    color = Gold,
                    fontSize = 13.sp
                )
            }
        }

        item {

            NextPrayerCard(
                prayerName = "ШОМ",
                prayerTime = prayerTimes.maghrib
            )
        }

        item {

            Text(
                text = "Вақти намоз",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            PrayerTimesCard(
                prayerTimes = prayerTimes
            )
        }

        item {

            Text(
                text = "Хизматҳо",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            ServiceGrid(
                onNamoz = onNamoz,
                onQuran = onQuran,
                onDuas = onDuas,
                onQibla = onQibla
            )
        }

        item {

            ServiceButton(
                icon = "☪",
                title = "99 номи Аллоҳ",
                subtitle = "Асмоул Ҳусно",
                onClick = onNames
            )
        }

        item {

            TasbehCard()
        }

        item {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Намози Ман",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Gold,
                fontSize = 13.sp
            )

            Text(
                text = "JABORZODA FAYZALI",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = SoftWhite,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun NextPrayerCard(
    prayerName: String,
    prayerTime: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardGreen
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {

            Text(
                text = "Намози навбатӣ",
                color = SoftWhite,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = prayerName,
                        color = Gold,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "То намози навбатӣ",
                        color = SoftWhite,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = prayerTime,
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PrayerTimesCard(
    prayerTimes: PrayerTimes
) {

    val prayers = listOf(
        "Бомдод" to prayerTimes.fajr,
        "Пешин" to prayerTimes.dhuhr,
        "Аср" to prayerTimes.asr,
        "Шом" to prayerTimes.maghrib,
        "Хуфтан" to prayerTimes.isha
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardGreen
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            prayers.forEach { prayer ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = prayer.first,
                        color = White,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = prayer.second,
                        color = Gold,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceGrid(
    onNamoz: () -> Unit,
    onQuran: () -> Unit,
    onDuas: () -> Unit,
    onQibla: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            ServiceButton(
                icon = "🕌",
                title = "Намоз",
                subtitle = "Тарзи хондан",
                onClick = onNamoz,
                modifier = Modifier.weight(1f)
            )

            ServiceButton(
                icon = "📖",
                title = "Қуръон",
                subtitle = "Сураҳо",
                onClick = onQuran,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            ServiceButton(
                icon = "🤲",
                title = "Дуоҳо",
                subtitle = "Зикрҳо",
                onClick = onDuas,
                modifier = Modifier.weight(1f)
            )

            ServiceButton(
                icon = "🧭",
                title = "Қибла",
                subtitle = "Самти Каъба",
                onClick = onQibla,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ServiceButton(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .height(105.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardGreen
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = icon,
                fontSize = 27.sp
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = title,
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = subtitle,
                color = SoftWhite,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun TasbehCard() {

    var count by remember {
        mutableStateOf(0)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardGreen
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "📿 Тасбеҳ",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = count.toString(),
                color = Gold,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = {
                        count++
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold,
                        contentColor = DarkGreen
                    )
                ) {
                    Text("Зикр +1")
                }

                OutlinedButton(
                    onClick = {
                        count = 0
                    }
                ) {
                    Text(
                        text = "Аз нав",
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun DuasScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            ScreenTitle(
                title = "🤲 Дуоҳо",
                subtitle = "Дуоҳои муҳим барои ҳар рӯз"
            )
        }

        items(IslamicData.duas) { dua ->

            DuaCard(dua)
        }
    }
}

@Composable
fun DuaCard(
    dua: DuaItem
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardGreen
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = dua.title,
                color = Gold,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = dua.arabic,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = White,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = dua.tajik,
                color = SoftWhite,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun NamesOfAllahScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "‹",
                color = Gold,
                fontSize = 38.sp,
                modifier = Modifier.clickable {
                    onBack()
                }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {

                Text(
                    text = "99 номи Аллоҳ",
                    color = White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Асмоул Ҳусно",
                    color = SoftWhite,
                    fontSize = 13.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(IslamicData.namesOfAllah) { name ->

                AllahNameCard(name)
            }
        }
    }
}

@Composable
fun AllahNameCard(
    name: AllahName
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardGreen
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        Gold,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = name.number.toString(),
                    color = DarkGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = name.arabic,
                    color = White,
                    fontSize = 22.sp
                )

                Text(
                    text = name.transliteration,
                    color = Gold,
                    fontSize = 13.sp
                )

                Text(
                    text = name.tajik,
                    color = SoftWhite,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun NamozScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            ScreenTitle(
                title = "🕌 Намоз",
                subtitle = "Намозҳои панҷвақта"
            )
        }

        items(NamozData.prayers) { prayer ->

            PrayerInfoCard(prayer)
        }
    }
}

@Composable
fun PrayerInfoCard(
    prayer: NamozInfo
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardGreen
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = prayer.name,
                        color = Gold,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = prayer.arabicName,
                        color = White,
                        fontSize = 19.sp
                    )
                }

                Text(
                    text = prayer.rakats,
                    color = Gold,
                    fontSize = 13.sp,
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = prayer.description,
                color = SoftWhite,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun QuranScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {

        ScreenTitle(
            title = "📖 Қуръон",
            subtitle = "Қуръони Карим"
        )

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardGreen
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Қуръони Карим",
                    color = Gold,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Сураҳо, оятҳо, матни арабӣ ва тарҷумаи тоҷикӣ.",
                    color = SoftWhite,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Ин бахш дар версияи аввал омода мешавад.",
                    color = White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun QiblaScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ScreenTitle(
            title = "🧭 Қибла",
            subtitle = "Самти Каъба"
        )

        Spacer(modifier = Modifier.height(35.dp))

        Box(
            modifier = Modifier
                .size(250.dp)
                .background(
                    CardGreen,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🕋",
                    fontSize = 65.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Қибла",
                    color = Gold,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Компас дар версияи пурра",
                    color = SoftWhite,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Телефонро ҳамвор нигоҳ доред.",
            color = White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ScreenTitle(
    title: String,
    subtitle: String
) {

    Column {

        Text(
            text = title,
            color = White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            color = SoftWhite,
            fontSize = 14.sp
        )
    }
}

@Composable
fun BottomNavigationBar(
    selectedScreen: AppScreen,
    onSelect: (AppScreen) -> Unit
) {

    NavigationBar(
        containerColor = Color(0xFF061512)
    ) {

        val screens = listOf(
            AppScreen.HOME,
            AppScreen.NAMOZ,
            AppScreen.QURAN,
            AppScreen.DUAS,
            AppScreen.QIBLA
        )

        screens.forEach { screen ->

            NavigationBarItem(
                selected = selectedScreen == screen,
                onClick = {
                    onSelect(screen)
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
                        fontSize = 10.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Gold,
                    selectedTextColor = Gold,
                    unselectedIconColor = SoftWhite,
                    unselectedTextColor = SoftWhite,
                    indicatorColor = CardGreen
                )
            )
        }
    }
}
