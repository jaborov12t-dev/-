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
            title = "Дуои саҳар",
            arabic = "اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا",
            tajik = "Эй Аллоҳ, бо ёрии Ту субҳ кардем ва бо ёрии Ту шом кардем."
        ),
        DuaItem(
            title = "Пеш аз хоб",
            arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            tajik = "Ба номи Ту, эй Аллоҳ, мемирам ва зинда мешавам."
        ),
        DuaItem(
            title = "Пеш аз хӯрок",
            arabic = "بِسْمِ اللَّهِ",
            tajik = "Ба номи Аллоҳ."
        ),
        DuaItem(
            title = "Баъд аз хӯрок",
            arabic = "الْحَمْدُ لِلَّهِ",
            tajik = "Ҳамду сано барои Аллоҳ аст."
        ),
        DuaItem(
            title = "Барои падару модар",
            arabic = "رَبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
            tajik = "Эй Парвардигори ман, ба онҳо раҳм кун, чунон ки маро дар кӯдакӣ парвариш карданд."
        ),
        DuaItem(
            title = "Дуои истиғфор",
            arabic = "أَسْتَغْفِرُ اللَّهَ",
            tajik = "Аз Аллоҳ омурзиш мехоҳам."
        ),
        DuaItem(
            title = "Дуои сафар",
            arabic = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا",
            tajik = "Пок аст Он Зоте, ки инро барои мо ром кард."
        ),
        DuaItem(
            title = "Ҳангоми тарс",
            arabic = "حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ",
            tajik = "Аллоҳ барои мо кофист ва Ӯ беҳтарин корсоз аст."
        ),
        DuaItem(
            title = "Барои осонӣ",
            arabic = "رَبِّ اشْرَحْ لِي صَدْرِي",
            tajik = "Эй Парвардигори ман, синаамро барои ман кушода гардон."
        ),
        DuaItem(
            title = "Барои ҳидоят",
            arabic = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
            tajik = "Моро ба роҳи рост ҳидоят кун."
        )
    )

    val namesOfAllah = listOf(

        AllahName(1, "الرَّحْمَنُ", "Ar-Rahmon", "Раҳмкунандаи ҳама"),
        AllahName(2, "الرَّحِيمُ", "Ar-Rahim", "Меҳрубони бисёр"),
        AllahName(3, "الْمَلِكُ", "Al-Malik", "Подшоҳ"),
        AllahName(4, "الْقُدُّوسُ", "Al-Quddus", "Пок"),
        AllahName(5, "السَّلَامُ", "As-Salam", "Манбаи саломатӣ"),
        AllahName(6, "الْمُؤْمِنُ", "Al-Mumin", "Амниятбахш"),
        AllahName(7, "الْمُهَيْمِنُ", "Al-Muhaymin", "Нигоҳбон"),
        AllahName(8, "الْعَزِيزُ", "Al-Aziz", "Пирӯзманд"),
        AllahName(9, "الْجَبَّارُ", "Al-Jabbar", "Қудратманд"),
        AllahName(10, "الْمُتَكَبِّرُ", "Al-Mutakabbir", "Бузургӣ соҳиби Ӯст"),

        AllahName(11, "الْخَالِقُ", "Al-Khaliq", "Офаридгор"),
        AllahName(12, "الْبَارِئُ", "Al-Bari", "Эҷодкунанда"),
        AllahName(13, "الْمُصَوِّرُ", "Al-Musawwir", "Суратбахш"),
        AllahName(14, "الْغَفَّارُ", "Al-Ghaffar", "Бисёр омурзанда"),
        AllahName(15, "الْقَهَّارُ", "Al-Qahhar", "Ғолиби мутлақ"),
        AllahName(16, "الْوَهَّابُ", "Al-Wahhab", "Бахшандаи бисёр"),
        AllahName(17, "الرَّزَّاقُ", "Ar-Razzaq", "Рӯзидиҳанда"),
        AllahName(18, "الْفَتَّاحُ", "Al-Fattah", "Кушояндаи роҳҳо"),
        AllahName(19, "الْعَلِيمُ", "Al-Alim", "Донои ҳама чиз"),
        AllahName(20, "الْقَابِضُ", "Al-Qabid", "Нигоҳдоранда"),

        AllahName(21, "الْبَاسِطُ", "Al-Basit", "Фаровонӣ бахшанда"),
        AllahName(22, "الْخَافِضُ", "Al-Khafid", "Пасткунанда"),
        AllahName(23, "الرَّافِعُ", "Ar-Rafi", "Баландкунанда"),
        AllahName(24, "الْمُعِزُّ", "Al-Muizz", "Иззатбахш"),
        AllahName(25, "الْمُذِلُّ", "Al-Mudhill", "Пасткунандаи саркашон"),
        AllahName(26, "السَّمِيعُ", "As-Sami", "Шунаво"),
        AllahName(27, "الْبَصِيرُ", "Al-Basir", "Бино"),
        AllahName(28, "الْحَكَمُ", "Al-Hakam", "Ҳакам"),
        AllahName(29, "الْعَدْلُ", "Al-Adl", "Одил"),
        AllahName(30, "اللَّطِيفُ", "Al-Latif", "Латифу меҳрубон"),

        AllahName(31, "الْخَبِيرُ", "Al-Khabir", "Огоҳ аз ҳама чиз"),
        AllahName(32, "الْحَلِيمُ", "Al-Halim", "Ҳалим"),
        AllahName(33, "الْعَظِيمُ", "Al-Azim", "Бузург"),
        AllahName(34, "الْغَفُورُ", "Al-Ghafur", "Омурзанда"),
        AllahName(35, "الشَّكُورُ", "Ash-Shakur", "Қадршинос"),
        AllahName(36, "الْعَلِيُّ", "Al-Ali", "Баландмартаба"),
        AllahName(37, "الْكَبِيرُ", "Al-Kabir", "Кабир"),
        AllahName(38, "الْحَفِيظُ", "Al-Hafiz", "Нигоҳбон"),
        AllahName(39, "الْمُقِيتُ", "Al-Muqit", "Ризқрасон"),
        AllahName(40, "الْحَسِيبُ", "Al-Hasib", "Ҳисобрас"),

        AllahName(41, "الْجَلِيلُ", "Al-Jalil", "Ҷалолатманд"),
        AllahName(42, "الْكَرِيمُ", "Al-Karim", "Карим"),
        AllahName(43, "الرَّقِيبُ", "Ar-Raqib", "Назораткунанда"),
        AllahName(44, "الْمُجِيبُ", "Al-Mujib", "Иҷобаткунандаи дуо"),
        AllahName(45, "الْوَاسِعُ", "Al-Wasi", "Фаровон"),
        AllahName(46, "الْحَكِيمُ", "Al-Hakim", "Ҳаким"),
        AllahName(47, "الْوَدُودُ", "Al-Wadud", "Дӯстдоранда"),
        AllahName(48, "الْمَجِيدُ", "Al-Majid", "Бузургвор"),
        AllahName(49, "الْبَاعِثُ", "Al-Baith", "Барангезанда"),
        AllahName(50, "الشَّهِيدُ", "Ash-Shahid", "Гувоҳ"),

        AllahName(51, "الْحَقُّ", "Al-Haqq", "Ҳақ"),
        AllahName(52, "الْوَكِيلُ", "Al-Wakil", "Корсоз"),
        AllahName(53, "الْقَوِيُّ", "Al-Qawiyy", "Қавӣ"),
        AllahName(54, "الْمَتِينُ", "Al-Matin", "Устувор"),
        AllahName(55, "الْوَلِيُّ", "Al-Wali", "Дӯст ва сарпараст"),
        AllahName(56, "الْحَمِيدُ", "Al-Hamid", "Сазовори ҳамд"),
        AllahName(57, "الْمُحْصِي", "Al-Muhsi", "Шуморкунанда"),
        AllahName(58, "الْمُبْدِئُ", "Al-Mubdi", "Оғозкунанда"),
        AllahName(59, "الْمُعِيدُ", "Al-Muid", "Бозгардонанда"),
        AllahName(60, "الْمُحْيِي", "Al-Muhyi", "Зиндакунанда"),

        AllahName(61, "الْمُمِيتُ", "Al-Mumit", "Миронанда"),
        AllahName(62, "الْحَيُّ", "Al-Hayy", "Зинда"),
        AllahName(63, "الْقَيُّومُ", "Al-Qayyum", "Пойдоркунанда"),
        AllahName(64, "الْوَاجِدُ", "Al-Wajid", "Ёбанда"),
        AllahName(65, "الْمَاجِدُ", "Al-Majid", "Соҳиби бузургӣ"),
        AllahName(66, "الْوَاحِدُ", "Al-Wahid", "Ягона"),
        AllahName(67, "الْأَحَدُ", "Al-Ahad", "Яктову ягона"),
        AllahName(68, "الصَّمَدُ", "As-Samad", "Бениёз"),
        AllahName(69, "الْقَادِرُ", "Al-Qadir", "Қодир"),
        AllahName(70, "الْمُقْتَدِرُ", "Al-Muqtadir", "Қудратманд"),

        AllahName(71, "الْمُقَدِّمُ", "Al-Muqaddim", "Пешбаранда"),
        AllahName(72, "الْمُؤَخِّرُ", "Al-Muakhkhir", "Ба таъхиргузоранда"),
        AllahName(73, "الْأَوَّلُ", "Al-Awwal", "Аввал"),
        AllahName(74, "الْآخِرُ", "Al-Akhir", "Охир"),
        AllahName(75, "الظَّاهِرُ", "Az-Zahir", "Зоҳир"),
        AllahName(76, "الْبَاطِنُ", "Al-Batin", "Пинҳон"),
        AllahName(77, "الْوَالِي", "Al-Wali", "Ҳоким"),
        AllahName(78, "الْمُتَعَالِي", "Al-Mutaali", "Бартар"),
        AllahName(79, "الْبَرُّ", "Al-Barr", "Некӯкор"),
        AllahName(80, "التَّوَابُ", "At-Tawwab", "Тавбапазир"),

        AllahName(81, "الْمُنْتَقِمُ", "Al-Muntaqim", "Ҷазодиҳанда"),
        AllahName(82, "الْعَفُوُّ", "Al-Afuww", "Авфкунанда"),
        AllahName(83, "الرَّؤُوفُ", "Ar-Rauf", "Бениҳоят меҳрубон"),
        AllahName(84, "مَالِكُ الْمُلْكِ", "Malikul-Mulk", "Соҳиби мулк"),
        AllahName(85, "ذُو الْجَلَالِ وَالْإِكْرَامِ", "Zul-Jalali wal-Ikram", "Соҳиби ҷалол ва икром"),
        AllahName(86, "الْمُقْسِطُ", "Al-Muqsit", "Одил"),
        AllahName(87, "الْجَامِعُ", "Al-Jami", "Ҷамъкунанда"),
        AllahName(88, "الْغَنِيُّ", "Al-Ghani", "Бениёз"),
        AllahName(89, "الْمُغْنِي", "Al-Mughni", "Бениёзкунанда"),
        AllahName(90, "الْمَانِعُ", "Al-Mani", "Боздоранда"),

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
