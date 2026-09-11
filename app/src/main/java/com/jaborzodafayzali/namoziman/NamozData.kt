package com.jaborzodafayzali.namoziman

data class NamozInfo(
    val name: String,
    val arabicName: String,
    val rakats: String,
    val description: String
)

object NamozData {

    val prayers = listOf(

        NamozInfo(
            name = "Бомдод",
            arabicName = "الفجر",
            rakats = "2 суннат + 2 фарз",
            description =
                "Намози субҳ пеш аз баромадани офтоб хонда мешавад."
        ),

        NamozInfo(
            name = "Пешин",
            arabicName = "الظهر",
            rakats = "4 суннат + 4 фарз + 2 суннат",
            description =
                "Намози пешин баъд аз гузаштани вақти нисфирӯзӣ хонда мешавад."
        ),

        NamozInfo(
            name = "Аср",
            arabicName = "العصر",
            rakats = "4 суннат + 4 фарз",
            description =
                "Намози аср дар вақти баъд аз пешин ва пеш аз ғуруби офтоб хонда мешавад."
        ),

        NamozInfo(
            name = "Шом",
            arabicName = "المغرب",
            rakats = "3 фарз + 2 суннат",
            description =
                "Намози шом баъд аз ғуруби офтоб хонда мешавад."
        ),

        NamozInfo(
            name = "Хуфтан",
            arabicName = "العشاء",
            rakats = "4 фарз + 2 суннат + 3 витр",
            description =
                "Намози хуфтан баъд аз торик шудани шаб хонда мешавад."
        )
    )
}
