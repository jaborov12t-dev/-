package com.jaborzodafayzali.namoziman

enum class AppScreen(
    val title: String,
    val icon: String
) {
    HOME(
        title = "Асосӣ",
        icon = "⌂"
    ),

    NAMOZ(
        title = "Намоз",
        icon = "🕌"
    ),

    QURAN(
        title = "Қуръон",
        icon = "📖"
    ),

    DUAS(
        title = "Дуоҳо",
        icon = "🤲"
    ),

    QIBLA(
        title = "Қибла",
        icon = "🧭"
    )
}
