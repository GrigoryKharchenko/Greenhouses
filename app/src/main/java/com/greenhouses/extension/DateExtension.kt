package com.greenhouses.extension

fun Int.formatDateValue(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

/**
 * Применяется к формату yyyy-mm-dd
 */
fun String.getDayAndMonth(): Pair<Int, Int> {
    val day = this.substring(startIndex = 9, endIndex = 10).toInt()
    val month = this.substring(startIndex = 6, endIndex = 7).toInt()
    return Pair(day, month)
}
