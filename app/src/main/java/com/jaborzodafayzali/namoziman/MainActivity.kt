package com.jaborzodafayzali.namoziman

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

private val DarkBackground = Color(0xFF061512)
private val CardColor = Color(0xFF0D2420)
private val CardColor2 = Color(0xFF12312B)
private val Gold = Color(0xFFD6B35A)
private val GoldLight = Color(0xFFF0D98A)
private val Green = Color(0xFF2E8B70)
private val White = Color(0xFFF7F7F2)
private val Gray = Color(0xFF9BAFA9)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NamozimanTheme {
                NamozimanApp()
            }
        }
    }
}

@Composable
fun NamozimanTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Gold,
            secondary = Green,
            background = DarkBackground,
            surface = CardColor,
            onPrimary = Color.Black,
            onBackground = White,
            onSurface = White
        ),
        content = content
    )
}

data class CurrentPlace(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

@Composable
fun NamozimanApp() {

    val context = LocalContext.current

    val locationHelper = remember {
        LocationHelper(context)
    }

    var currentPlace by remember {
        mutableStateOf<CurrentPlace?>(null)
    }

    var locationLoading by remember {
        mutableStateOf(false)
    }

    var locationError by remember {
        mutableStateOf(false)
    }

    var selectedScreen by remember {
        mutableStateOf(AppScreen.HOME)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fine =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION]
                    ?: false

            val coarse =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION]
                    ?: false

            if (fine || coarse) {
                locationLoading = true
                locationError = false

                locationHelper.getCurrentLocation { location ->

                    if (location != null) {

                        val cityInfo = CityHelper.getCity(
                            context,
                            location.latitude,
                            location.longitude
                        )

                        currentPlace = CurrentPlace(
                            city = cityInfo.city,
                            country = cityInfo.country,
                            latitude = location.latitude,
                            longitude = location.longitude
                        )

                        locationLoading = false

                    } else {
                        locationLoading = false
                        locationError = true
                    }
                }

            } else {
                locationLoading = false
                locationError = true
            }
        }

    fun loadLocation() {

        if (!locationHelper.hasPermission()) {

            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            return
        }

        locationLoading = true
        locationError = false

        locationHelper.getCurrentLocation { location ->

            if (location != null) {

                val cityInfo = CityHelper.getCity(
                    context,
                    location.latitude,
                    location.longitude
                )

                currentPlace = CurrentPlace(
                    city = cityInfo.city,
                    country = cityInfo.country,
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                locationLoading = false

            } else {
                locationLoading = false
                locationError = true
            }
        }
    }

    LaunchedEffect(Unit) {
        loadLocation()
    }

    DisposableEffect(locationHelper) {

        if (locationHelper.hasPermission()) {

            locationHelper.startLocationUpdates { location ->

                val cityInfo = CityHelper.getCity(
                    context,
                    location.latitude,
                    location.longitude
                )

                currentPlace = CurrentPlace(
                    city = cityInfo.city,
                    country = cityInfo.country,
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            }
        }

        onDispose {
            locationHelper.stopLocationUpdates()
        }
    }

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen,
                onSelected = {
                    selectedScreen = it
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when (selectedScreen) {

                AppScreen.HOME -> {
                    HomeScreen(
                        place = currentPlace,
                        loading = locationLoading,
                        locationError = locationError,
                        onRefreshLocation = {
                            loadLocation()
                        },
                        onOpen = {
                            selectedScreen = it
                        }
                    )
                }

                AppScreen.NAMOZ -> {
                    NamozScreen()
                }

                AppScreen.QURAN -> {
                    QuranScreen()
                }

                AppScreen.DUAS -> {
                    DuasScreen()
                }

                AppScreen.QIBLA -> {

                    QiblaScreen(
                        place = currentPlace,
                        onRefreshLocation = {
                            loadLocation()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    place: CurrentPlace?,
    loading: Boolean,
    locationError: Boolean,
    onRefreshLocation: () -> Unit,
    onOpen: (AppScreen) -> Unit
) {

    var now by remember {
        mutableStateOf(Calendar.getInstance())
    }

    LaunchedEffect(Unit) {

        while (true) {

            now = Calendar.getInstance()

            delay(1000)
        }
    }

    val latitude =
        place?.latitude ?: 38.5598

    val longitude =
        place?.longitude ?: 68.7870

    val timezone =
        TimeZone.getDefault()
            .getOffset(now.timeInMillis)
            .toDouble() / 3_600_000.0

    val prayerTimes =
        remember(
            latitude,
            longitude,
            now.get(Calendar.YEAR),
            now.get(Calendar.DAY_OF_YEAR),
            timezone
        ) {

            PrayerTimesCalculator.calculate(
                latitude = latitude,
                longitude = longitude,
                timezone = timezone,
                date = now
            )
        }

    val prayers = listOf(
        "Бомдод" to prayerTimes.fajr,
        "Пешин" to prayerTimes.dhuhr,
        "Аср" to prayerTimes.asr,
        "Шом" to prayerTimes.maghrib,
        "Хуфтан" to prayerTimes.isha
    )

    val nextPrayer =
        findNextPrayer(
            prayers,
            now
        )

    val dateText =
        SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("tg")
        ).format(now.time)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 24.dp
        )
    ) {

        item {

            Text(
                text = "Ассалому алайкум 🤍",
                color = White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = dateText,
                color = Gray,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            LocationCard(
                place = place,
                loading = loading,
                error = locationError,
                onRefresh = onRefreshLocation
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            NextPrayerCard(
                prayer = nextPrayer.first,
                time = nextPrayer.second,
                now = now
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Вақти намоз",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }

        items(prayers) { prayer ->

            PrayerTimeCard(
                name = prayer.first,
                time = prayer.second
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        item {

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Хидматҳо",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            HomeActionCard(
                icon = "🕌",
                title = "Намоз",
                subtitle = "Тарзи хондани намоз",
                onClick = {
                    onOpen(AppScreen.NAMOZ)
                }
            )

            HomeActionCard(
                icon = "📖",
                title = "Қуръон",
                subtitle = "Сураҳо ва оятҳо",
                onClick = {
                    onOpen(AppScreen.QURAN)
                }
            )

            HomeActionCard(
                icon = "🤲",
                title = "Дуоҳо",
                subtitle = "Дуоҳои ҳаррӯза",
                onClick = {
                    onOpen(AppScreen.DUAS)
                }
            )

            HomeActionCard(
                icon = "🧭",
                title = "Қибла",
                subtitle = "Самти Каъба",
                onClick = {
                    onOpen(AppScreen.QIBLA)
                }
            )
        }
    }
}

@Composable
fun LocationCard(
    place: CurrentPlace?,
    loading: Boolean,
    error: Boolean,
    onRefresh: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        ),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "📍",
                    fontSize = 26.sp
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            if (loading)
                                "Ҷойгиршавӣ муайян шуда истодааст..."
                            else if (place != null)
                                "${place.city}, ${place.country}"
                            else
                                "Ҷойгиршавӣ муайян нашудааст",
                        color = White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (place != null) {

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = String.format(
                                Locale.US,
                                "%.5f, %.5f",
                                place.latitude,
                                place.longitude
                            ),
                            color = Gray,
                            fontSize = 12.sp
                        )
                    }

                    if (error) {

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "GPS-ро фаъол кунед ва иҷозати ҷойгиршавиро диҳед.",
                            color = GoldLight,
                            fontSize = 12.sp
                        )
                    }
                }

                TextButton(
                    onClick = onRefresh
                ) {
                    Text(
                        text = "↻",
                        color = Gold,
                        fontSize = 28.sp
                    )
                }
            }
        }
    }
}

@Composable
fun NextPrayerCard(
    prayer: String,
    time: String,
    now: Calendar
) {

    val remaining =
        remember(
            prayer,
            time,
            now.get(Calendar.MINUTE),
            now.get(Calendar.SECOND)
        ) {
            calculateRemaining(time, now)
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardColor2
        ),
        shape = RoundedCornerShape(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Намози навбатӣ",
                color = Gray,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = prayer,
                color = GoldLight,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = time,
                color = White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "То намози навбатӣ: $remaining",
                color = Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun PrayerTimeCard(
    name: String,
    time: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = prayerIcon(name),
                fontSize = 22.sp
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Text(
                text = name,
                color = White,
                fontSize = 17.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = time,
                color = GoldLight,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun prayerIcon(name: String): String {

    return when (name) {
        "Бомдод" -> "🌅"
        "Пешин" -> "☀️"
        "Аср" -> "🌤️"
        "Шом" -> "🌇"
        "Хуфтан" -> "🌙"
        else -> "🕌"
    }
}

@Composable
fun HomeActionCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF193A32)),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = icon,
                    fontSize = 25.sp
                )
            }

            Spacer(
                modifier = Modifier.width(15.dp)
            )

            Column {

                Text(
                    text = title,
                    color = White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitle,
                    color = Gray,
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
            .background(DarkBackground)
            .padding(16.dp),
        contentPadding = PaddingValues(
            top = 20.dp,
            bottom = 30.dp
        )
    ) {

        item {

            Text(
                text = "🕌 Намоз",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Намозҳои панҷвақта",
                color = Gray,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )
        }

        items(NamozData.prayers) { prayer ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardColor
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = prayer.name,
                                color = GoldLight,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = prayer.arabicName,
                                color = Gray,
                                fontSize = 18.sp
                            )
                        }

                        Text(
                            text = prayer.rakats,
                            color = White,
                            fontSize = 13.sp,
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = prayer.description,
                        color = Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun QuranScreen() {

    var search by remember {
        mutableStateOf("")
    }

    var selectedSurah by remember {
        mutableStateOf<QuranSurah?>(null)
    }

    if (selectedSurah != null) {

        QuranReaderScreen(
            surah = selectedSurah!!,
            onBack = {
                selectedSurah = null
            }
        )

        return
    }

    val filtered =
        QuranData.surahs.filter {

            it.arabicName.contains(search) ||
                    it.tajikName.contains(search) ||
                    it.number.toString() == search
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {

        Text(
            text = "📖 Қуръон",
            color = White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Ҷустуҷӯи сура...")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gold,
                unfocusedBorderColor = Green,
                focusedTextColor = White,
                unfocusedTextColor = White
            )
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LazyColumn {

            items(filtered) { surah ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            selectedSurah = surah
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = CardColor
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF193A32)),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = surah.number.toString(),
                                color = GoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(14.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = surah.tajikName,
                                color = White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = surah.arabicName,
                                color = GoldLight,
                                fontSize = 18.sp
                            )
                        }

                        Text(
                            text = "${surah.ayahCount} оят",
                            color = Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuranReaderScreen(
    surah: QuranSurah,
    onBack: () -> Unit
) {

    val ayahs =
        QuranData.getAyahs(surah.number)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(
                onClick = onBack
            ) {

                Text(
                    text = "← Бозгашт",
                    color = Gold
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column {

                Text(
                    text = surah.tajikName,
                    color = White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = surah.arabicName,
                    color = Gray
                )
            }
        }

        if (ayahs.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Матни пурраи ин сура ҳоло илова нашудааст.",
                    color = Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(30.dp)
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(
                    bottom = 30.dp
                )
            ) {

                items(ayahs) { ayah ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardColor
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            Text(
                                text = "${ayah.number}",
                                color = Gold,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text = ayah.arabic,
                                color = White,
                                fontSize = 24.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = ayah.tajik,
                                color = Gray,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DuasScreen() {

    var showNames by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "🤲 Дуоҳо",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            TextButton(
                onClick = {
                    showNames = !showNames
                }
            ) {

                Text(
                    text =
                        if (showNames)
                            "Дуоҳо"
                        else
                            "99 ном",
                    color = Gold
                )
            }
        }

        if (showNames) {

            LazyColumn(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {

                items(
                    IslamicData.allahNames
                ) { name ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardColor
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ) {

                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = name.number.toString(),
                                color = Gold,
                                modifier = Modifier.width(35.dp)
                            )

                            Column {

                                Text(
                                    text = name.arabic,
                                    color = GoldLight,
                                    fontSize = 20.sp
                                )

                                Text(
                                    text = name.tajik,
                                    color = White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {

                items(
                    IslamicData.duas
                ) { dua ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardColor
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            Text(
                                text = dua.title,
                                color = GoldLight,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = dua.arabic,
                                color = White,
                                fontSize = 21.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text = dua.tajik,
                                color = Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QiblaScreen(
    place: CurrentPlace?,
    onRefreshLocation: () -> Unit
) {

    val context = LocalContext.current

    var heading by remember {
        mutableStateOf(0f)
    }

    val compass = remember {
        QiblaCompass(
            context = context
        ) {
            heading = it
        }
    }

    DisposableEffect(compass) {

        compass.start()

        onDispose {
            compass.stop()
        }
    }

    val latitude =
        place?.latitude ?: 38.5598

    val longitude =
        place?.longitude ?: 68.7870

    val qibla =
        QiblaHelper.calculateQiblaBearing(
            latitude,
            longitude
        )

    val direction =
        QiblaHelper.directionName(qibla)

    val difference =
        ((qibla - heading + 540f) % 360f) - 180f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "🧭 Қибла",
            color = White,
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = place?.let {
                "${it.city}, ${it.country}"
            } ?: "Ҷойгиршавӣ муайян нашудааст",
            color = Gray,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Box(
            modifier = Modifier
                .size(270.dp)
                .clip(CircleShape)
                .background(CardColor),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "N",
                color = GoldLight,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 15.dp)
            )

            Text(
                text = "S",
                color = Gray,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 15.dp)
            )

            Text(
                text = "W",
                color = Gray,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 15.dp)
            )

            Text(
                text = "E",
                color = Gray,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
            )

            Text(
                text = "🕋",
                fontSize = 50.sp,
                modifier = Modifier.offset(
                    x = 0.dp,
                    y = 0.dp
                )
            )
        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = CardColor
            ),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Самти Қибла",
                    color = Gray
                )

                Text(
                    text = "${qibla.toInt()}°",
                    color = GoldLight,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = direction,
                    color = White,
                    fontSize = 17.sp
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "Телефонро оҳиста гардонед.",
                    color = Gray,
                    fontSize = 13.sp
                )

                Text(
                    text = "Самти телефон: ${heading.toInt()}°",
                    color = Gray,
                    fontSize = 13.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Фарқ аз Қибла: ${kotlin.math.abs(difference).toInt()}°",
                    color = Gold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Button(
            onClick = onRefreshLocation,
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            ),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "📍 Навсозии ҷойгиршавӣ",
                color = White
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedScreen: AppScreen,
    onSelected: (AppScreen) -> Unit
) {

    NavigationBar(
        containerColor = Color(0xFF081C18)
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
                    onSelected(screen)
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
                    unselectedIconColor = Gray,
                    unselectedTextColor = Gray,
                    indicatorColor = Color(0xFF193A32)
                )
            )
        }
    }
}

fun findNextPrayer(
    prayers: List<Pair<String, String>>,
    now: Calendar
): Pair<String, String> {

    val currentMinutes =
        now.get(Calendar.HOUR_OF_DAY) * 60 +
                now.get(Calendar.MINUTE)

    for (prayer in prayers) {

        val parts =
            prayer.second.split(":")

        if (parts.size == 2) {

            val hour =
                parts[0].toIntOrNull() ?: continue

            val minute =
                parts[1].toIntOrNull() ?: continue

            val prayerMinutes =
                hour * 60 + minute

            if (prayerMinutes > currentMinutes) {
                return prayer
            }
        }
    }

    return prayers.first()
}

fun calculateRemaining(
    time: String,
    now: Calendar
): String {

    val parts =
        time.split(":")

    if (parts.size != 2) {
        return "--:--:--"
    }

    val hour =
        parts[0].toIntOrNull() ?: return "--:--:--"

    val minute =
        parts[1].toIntOrNull() ?: return "--:--:--"

    val target =
        Calendar.getInstance()

    target.timeInMillis = now.timeInMillis

    target.set(
        Calendar.HOUR_OF_DAY,
        hour
    )

    target.set(
        Calendar.MINUTE,
        minute
    )

    target.set(
        Calendar.SECOND,
        0
    )

    target.set(
        Calendar.MILLISECOND,
        0
    )

    if (target.timeInMillis <= now.timeInMillis) {

        target.add(
            Calendar.DAY_OF_YEAR,
            1
        )
    }

    var seconds =
        (target.timeInMillis - now.timeInMillis) / 1000

    val hours =
        seconds / 3600

    seconds %= 3600

    val minutes =
        seconds / 60

    seconds %= 60

    return "%02d:%02d:%02d".format(
        hours,
        minutes,
        seconds
    )
}
