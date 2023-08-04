package gr.dipae.thesisfitnessapp.util.ext

import java.math.BigDecimal
import java.math.RoundingMode

fun Double?.toDoubleWithSpecificDecimals(numberOfDecimal: Int = 2): Double {
    return this?.let {
        val decimal = BigDecimal(it)
        val rounded = decimal.setScale(numberOfDecimal, RoundingMode.DOWN)
        return rounded.toDouble()
    } ?: 0.0
}