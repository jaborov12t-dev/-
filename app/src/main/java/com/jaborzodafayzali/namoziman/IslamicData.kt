package com.jaborzodafayzali.namoziman

data class DuaItem(
    val title: String,
    val arabic: String,
    val tajik: String
)

data class AllahName(
    val number: Int,
    val arabic: String,
    val transliteration: String,
    val tajik: String
)

object IslamicData {

    val duas = listOf(
        DuaItem(
            "Дуои оғоз",
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            "Ба номи Аллоҳи бахшояндаи меҳрубон"
        ),
        DuaItem(
            "Дуои субҳ",
            "اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا",
            "Эй Аллоҳ, ба ёрии Ту субҳ кардем ва ба ёрии Ту шом мекунем."
        ),
        DuaItem(
            "Дуои пеш аз хӯрок",
            "بِسْمِ اللَّهِ",
            "Ба номи Аллоҳ"
        ),
        DuaItem(
            "Дуои баъд аз хӯрок",
            "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنِي هَذَا",
            "Ситоиш барои Аллоҳе, ки ин ғизоро ба ман дод."
        ),
        DuaItem(
            "Дуои падару модар",
            "رَبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
            "Парвардигоро, ба онҳо раҳм кун, чунон ки маро дар хурдӣ тарбия карданд."
        ),
        DuaItem(
            "Дуои бахшиш",
            "رَبِّ اغْفِرْ لِي",
            "Парвардигоро, маро биёмурз."
        ),
        DuaItem(
            "Дуои роҳ",
            "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا",
            "Пок аст Он касе, ки инро барои мо ром кард."
        ),
        DuaItem(
            "Дуои ҳифз",
            "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ",
            "Ба калимаҳои комили Аллоҳ паноҳ мебарам."
        ),
        DuaItem(
            "Дуои ризқ",
            "اللَّهُمَّ ارْزُقْنِي رِزْقًا حَلَالًا طَيِّبًا",
            "Эй Аллоҳ, ба ман ризқи ҳалолу пок ато кун."
        ),
        DuaItem(
            "Дуои некӣ",
            "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً",
            "Парвардигоро, дар дунё ва охират ба мо некӣ ато кун."
        )
    )

    val allahNames = listOf(
        AllahName(1, "الرَّحْمَنُ", "Ar-Rahman", "Бахшояндаи бузург"),
        AllahName(2, "الرَّحِيمُ", "Ar-Rahim", "Меҳрубон"),
        AllahName(3, "الْمَلِكُ", "Al-Malik", "Подшоҳ"),
        AllahName(4, "الْقُدُّوسُ", "Al-Quddus", "Пок"),
        AllahName(5, "السَّلَامُ", "As-Salam", "Саломаткунанда"),
        AllahName(6, "الْمُؤْمِنُ", "Al-Mu'min", "Амонбахш"),
        AllahName(7, "الْمُهَيْمِنُ", "Al-Muhaymin", "Нигоҳбон"),
        AllahName(8, "الْعَزِيزُ", "Al-Aziz", "Бузург ва ғолиб"),
        AllahName(9, "الْجَبَّارُ", "Al-Jabbar", "Ҷаббор"),
        AllahName(10, "الْمُتَكَبِّرُ", "Al-Mutakabbir", "Бузургвор"),

        AllahName(11, "الْخَالِقُ", "Al-Khaliq", "Офаридгор"),
        AllahName(12, "الْبَارِئُ", "Al-Bari", "Пайдооваранда"),
        AllahName(13, "الْمُصَوِّرُ", "Al-Musawwir", "Суратдиҳанда"),
        AllahName(14, "الْغَفَّارُ", "Al-Ghaffar", "Бисёр омурзанда"),
        AllahName(15, "الْقَهَّارُ", "Al-Qahhar", "Ғолиби мутлақ"),
        AllahName(16, "الْوَهَّابُ", "Al-Wahhab", "Бахшандаи бисёр"),
        AllahName(17, "الرَّزَّاقُ", "Ar-Razzaq", "Ризқдиҳанда"),
        AllahName(18, "الْفَتَّاحُ", "Al-Fattah", "Кушоянда"),
        AllahName(19, "الْعَلِيمُ", "Al-Alim", "Донои мутлақ"),
        AllahName(20, "الْقَابِضُ", "Al-Qabid", "Нигоҳдоранда"),

        AllahName(21, "الْبَاسِطُ", "Al-Basit", "Фаровонӣ бахшанда"),
        AllahName(22, "الْخَافِضُ", "Al-Khafid", "Пасткунанда"),
        AllahName(23, "الرَّافِعُ", "Ar-Rafi", "Баландбардоранда"),
        AllahName(24, "الْمُعِزُّ", "Al-Mu'izz", "Иззатдиҳанда"),
        AllahName(25, "الْمُذِلُّ", "Al-Mudhill", "Хоркунанда"),
        AllahName(26, "السَّمِيعُ", "As-Sami", "Шунавандаи ҳама"),
        AllahName(27, "الْبَصِيرُ", "Al-Basir", "Бинандаи ҳама"),
        AllahName(28, "الْحَكَمُ", "Al-Hakam", "Ҳакам"),
        AllahName(29, "الْعَدْلُ", "Al-Adl", "Одил"),
        AllahName(30, "اللَّطِيفُ", "Al-Latif", "Латиф ва меҳрубон"),

        AllahName(31, "الْخَبِيرُ", "Al-Khabir", "Огоҳ"),
        AllahName(32, "الْحَلِيمُ", "Al-Halim", "Бурдбор"),
        AllahName(33, "الْعَظِيمُ", "Al-Azim", "Бузург"),
        AllahName(34, "الْغَفُورُ", "Al-Ghafur", "Омурзанда"),
        AllahName(35, "الشَّكُورُ", "Ash-Shakur", "Қадршинос"),
        AllahName(36, "الْعَلِيُّ", "Al-Ali", "Баландмартаба"),
        AllahName(37, "الْكَبِيرُ", "Al-Kabir", "Бузургтарин"),
        AllahName(38, "الْحَفِيظُ", "Al-Hafiz", "Ҳифзкунанда"),
        AllahName(39, "الْمُقِيتُ", "Al-Muqit", "Қувватбахш"),
        AllahName(40, "الْحَسِيبُ", "Al-Hasib", "Ҳисобкунанда"),

        AllahName(41, "الْجَلِيلُ", "Al-Jalil", "Ҷалолатманд"),
        AllahName(42, "الْكَرِيمُ", "Al-Karim", "Карим"),
        AllahName(43, "الرَّقِيبُ", "Ar-Raqib", "Назораткунанда"),
        AllahName(44, "الْمُجِيبُ", "Al-Mujib", "Дуоро иҷобаткунанда"),
        AllahName(45, "الْوَاسِعُ", "Al-Wasi", "Фаровон"),
        AllahName(46, "الْحَكِيمُ", "Al-Hakim", "Ҳаким"),
        AllahName(47, "الْوَدُودُ", "Al-Wadud", "Муҳаббаткунанда"),
        AllahName(48, "الْمَجِيدُ", "Al-Majid", "Ҷалолатманд"),
        AllahName(49, "الْبَاعِثُ", "Al-Ba'ith", "Барангезанда"),
        AllahName(50, "الشَّهِيدُ", "Ash-Shahid", "Шоҳид"),

        AllahName(51, "الْحَقُّ", "Al-Haqq", "Ҳақ"),
        AllahName(52, "الْوَكِيلُ", "Al-Wakil", "Такягоҳ"),
        AllahName(53, "الْقَوِيُّ", "Al-Qawiyy", "Қавӣ"),
        AllahName(54, "الْمَتِينُ", "Al-Matin", "Мустаҳкам"),
        AllahName(55, "الْوَلِيُّ", "Al-Wali", "Дӯст ва сарпараст"),
        AllahName(56, "الْحَمِيدُ", "Al-Hamid", "Ситоишшуда"),
        AllahName(57, "الْمُحْصِي", "Al-Muhsi", "Шуморкунанда"),
        AllahName(58, "الْمُبْدِئُ", "Al-Mubdi", "Оғозкунанда"),
        AllahName(59, "الْمُعِيدُ", "Al-Muid", "Бозгардонанда"),
        AllahName(60, "الْمُحْيِي", "Al-Muhyi", "Зиндакунанда"),

        AllahName(61, "الْمُمِيتُ", "Al-Mumit", "Миронанда"),
        AllahName(62, "الْحَيُّ", "Al-Hayy", "Зиндаи абадӣ"),
        AllahName(63, "الْقَيُّومُ", "Al-Qayyum", "Пойдоркунанда"),
        AllahName(64, "الْوَاجِدُ", "Al-Wajid", "Ёбанда"),
        AllahName(65, "الْمَاجِدُ", "Al-Majid", "Шарафманд"),
        AllahName(66, "الْوَاحِدُ", "Al-Wahid", "Ягона"),
        AllahName(67, "الْأَحَدُ", "Al-Ahad", "Якто"),
        AllahName(68, "الصَّمَدُ", "As-Samad", "Бениёз"),
        AllahName(69, "الْقَادِرُ", "Al-Qadir", "Қодир"),
        AllahName(70, "الْمُقْتَدِرُ", "Al-Muqtadir", "Тавоно"),

        AllahName(71, "الْمُقَدِّمُ", "Al-Muqaddim", "Пешбаранда"),
        AllahName(72, "الْمُؤَخِّرُ", "Al-Mu'akhkhir", "Ақибандоз"),
        AllahName(73, "الْأَوَّلُ", "Al-Awwal", "Аввал"),
        AllahName(74, "الْآخِرُ", "Al-Akhir", "Охир"),
        AllahName(75, "الظَّاهِرُ", "Az-Zahir", "Зоҳир"),
        AllahName(76, "الْبَاطِنُ", "Al-Batin", "Ботин"),
        AllahName(77, "الْوَالِي", "Al-Wali", "Ҳоким"),
        AllahName(78, "الْمُتَعَالِي", "Al-Muta'ali", "Баландмартаба"),
        AllahName(79, "الْبَرُّ", "Al-Barr", "Некӯкор"),
        AllahName(80, "التَّوَابُ", "At-Tawwab", "Тавбапазируфта"),

        AllahName(81, "الْمُنْتَقِمُ", "Al-Muntaqim", "Интиқомгиранда"),
        AllahName(82, "الْعَفُوُّ", "Al-Afuww", "Бисёр бахшанда"),
        AllahName(83, "الرَّؤُوفُ", "Ar-Ra'uf", "Меҳрубони бисёр"),
        AllahName(84, "مَالِكُ الْمُلْكِ", "Malik-ul-Mulk", "Соҳиби мулк"),
        AllahName(85, "ذُو الْجَلَالِ وَالْإِكْرَامِ", "Dhul-Jalali wal-Ikram", "Соҳиби ҷалол ва икром"),
        AllahName(86, "الْمُقْسِطُ", "Al-Muqsit", "Одил"),
        AllahName(87, "الْجَامِعُ", "Al-Jami", "Ҷамъкунанда"),
        AllahName(88, "الْغَنِيُّ", "Al-Ghani", "Бениёз"),
        AllahName(89, "الْمُغْنِي", "Al-Mughni", "Бениёзкунанда"),
        AllahName(90, "الْمَانِعُ", "Al-Mani", "Манъкунанда"),

        AllahName(91, "الضَّارَّ", "Ad-Darr", "Қудрати расонидани зарар"),
        AllahName(92, "النَّافِعُ", "An-Nafi", "Фоидарасон"),
        AllahName(93, "النُّورُ", "An-Nur", "Нур"),
        AllahName(94, "الْهَادِي", "Al-Hadi", "Ҳидояткунанда"),
        AllahName(95, "الْبَدِيعُ", "Al-Badi", "Офаринандаи беҳамто"),
        AllahName(96, "الْبَاقِي", "Al-Baqi", "Ҷовид"),
        AllahName(97, "الْوَارِثُ", "Al-Warith", "Меросбаранда"),
        AllahName(98, "الرَّشِيدُ", "Ar-Rashid", "Роҳнамои дуруст"),
        AllahName(99, "الصَّبُورُ", "As-Sabur", "Босабр")
    )
}
