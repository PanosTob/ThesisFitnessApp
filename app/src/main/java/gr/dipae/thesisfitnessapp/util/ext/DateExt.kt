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