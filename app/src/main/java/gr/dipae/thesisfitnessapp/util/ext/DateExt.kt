package gr.dipae.thesisfitnessapp.util.ext

import gr.dipae.thesisfitnessapp.util.DATE
import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toDate(format: String = DATE): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(this)
    } catch (e: Exception) {
        ""
    }
}

fun String.convertDateFormat(sourcePattern: String = DATE, desiredPattern: String): String {
    val sourceDateFormat = SimpleDateFormat(sourcePattern, Locale.getDefault())
    val desiredDateFormat = SimpleDateFormat(desiredPattern, Locale.getDefault())
    return try {
        val date = sourceDateFormat.parse(this)
        date?.let { desiredDateFormat.format(date) } ?: ""
    } catch (e: java.lang.Exception) {
        ""
    }
}