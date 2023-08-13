package gr.dipae.thesisfitnessapp.util.ext

import java.text.DecimalFormat

val Double.formatAmountWith2Decimals: String
    get() =
        DecimalFormat().apply {

            decimalFormatSymbols = decimalFormatSymbols.apply {
                decimalSeparator = ','
                groupingSeparator = '.'
            }
            isDecimalSeparatorAlwaysShown = false
            minimumFractionDigits = 2
        }.format(this)