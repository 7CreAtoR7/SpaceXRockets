package ru.ilya.spacexrockets.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun Int.fromUnixToDate(): String {
    val date = Date(this * 1000L)
    val day = SimpleDateFormat("dd", Locale("ru")).format(date)
    val formattedDay = if (day.startsWith("0")) day.substring(1) else day

    val format = SimpleDateFormat("dd MMMM, yyyy", Locale("ru"))
    val formattedDate = format.format(date) // Форматируем дату с помощью SimpleDateFormat

    return formattedDate.replace(formattedDay, formattedDay)
}

fun String.formatToRusDate(): String {
    val inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val outputDateFormat = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale("ru", "RU"))
    val date = LocalDate.parse(this, inputDateFormat)
    return date.format(outputDateFormat)
}

fun Int.toShortPriceName(): String {
    val absValue = kotlin.math.abs(this)
    return when {
        absValue >= 1_000_000_000 -> {
            "$${absValue / 1_000_000_000} млрд"
        }
        absValue >= 1_000_000 -> {
            "$${absValue / 1_000_000} млн"
        }
        absValue >= 1_000 -> {
            "$${absValue / 1_000} тыс"
        }
        else -> "$$this"
    }
}

fun Double.toEasyReadStringNumber(): String {
    val numberString = this.toLong().toString()
    val reversedNumberString = numberString.reversed()
    val formattedString = StringBuilder()

    for (i in reversedNumberString.indices) {
        if (i != 0 && i % 3 == 0) {
            formattedString.append(' ')
        }
        formattedString.append(reversedNumberString[i])
    }

    return formattedString.reverse().toString()
}
