package ru.ilya.spacexrockets.util

import java.text.SimpleDateFormat
import java.util.*

fun Int.fromUnixToDate(): String {
    val date = Date(this * 1000L)
    val day = SimpleDateFormat("dd", Locale("ru")).format(date)
    val formattedDay = if (day.startsWith("0")) day.substring(1) else day

    val format = SimpleDateFormat("dd MMMM, yyyy", Locale("ru"))
    val formattedDate = format.format(date) // Форматируем дату с помощью SimpleDateFormat

    return formattedDate.replace(formattedDay, formattedDay)
}