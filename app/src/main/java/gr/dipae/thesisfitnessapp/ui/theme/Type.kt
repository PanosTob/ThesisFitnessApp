package gr.dipae.thesisfitnessapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import gr.dipae.thesisfitnessapp.R

val openSansFontFamily = FontFamily(
    Font(R.font.open_sans_semi_bold, FontWeight.SemiBold),
    Font(R.font.open_sans_bold, FontWeight.Bold)
)

val lexendDecaFontFamily = FontFamily(
    Font(R.font.lexend_deca_medium, FontWeight.Medium)
)

val karantinaFontFamily = FontFamily(
    Font(R.font.karantina_light, FontWeight.Light),
    Font(R.font.karantina_regular, FontWeight.Normal),
    Font(R.font.karantina_bold, FontWeight.Bold)
)

val thesisFitnessTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = karantinaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = lexendDecaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = lexendDecaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = lexendDecaFontFamily,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = lexendDecaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = lexendDecaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    )
)

val FontSize_14sp = 14.sp
val FontSize_16sp = 16.sp
val FontSize_18sp = 18.sp
val FontSize_20sp = 20.sp