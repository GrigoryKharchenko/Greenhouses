package com.greenhouses.domain.service

import com.greenhouses.R
import javax.inject.Inject

class ZodiacService @Inject constructor() {

    fun checkZodiac(day: Int, month: Int): Int {
        return when {
            (month == 1 && day >= 20 || month == 2 && day <= 19)
            -> R.string.zodiac_aquaris
            (month == 2 && day >= 20 || month == 3 && day <= 20)
            -> R.string.zodiac_pisces
            (month == 12 && day >= 22 || month == 1 && day <= 19)
            -> R.string.zodiac_capricon
            (month == 11 && day >= 23 || month == 12 && day <= 21)
            -> R.string.zodiac_sagittarius
            (month == 10 && day >= 23 || month == 11 && day <= 22)
            -> R.string.zodiac_scorpio
            (month == 9 && day >= 23 || month == 10 && day <= 22)
            -> R.string.zodiac_libra
            (month == 8 && day >= 23 || month == 9 && day <= 22)
            -> R.string.zodiac_virgo
            (month == 7 && day >= 23 || month == 8 && day <= 22)
            -> R.string.zodiac_leo
            (month == 6 && day >= 21 || month == 7 && day <= 22)
            -> R.string.zodiac_cancer
            (month == 5 && day >= 21 || month == 6 && day <= 20)
            -> R.string.zodiac_gemini
            (month == 4 && day >= 21 || month == 5 && day <= 20)
            -> R.string.zodiac_taurus
            (month == 3 && day >= 21 || month == 4 && day <= 20)
            -> R.string.zodiac_laries
            else -> R.string.zodiac_error
        }
    }
}
