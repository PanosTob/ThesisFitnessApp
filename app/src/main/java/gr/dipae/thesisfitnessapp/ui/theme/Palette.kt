package gr.dipae.thesisfitnessapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val lightColorsPalette = lightColorScheme(
    primary = ColorPrimary,
    secondary = ColorSecondary,
    tertiary = ColorBottomNavBar,
    background = Color.White,
    surface = Color.Black,
    outline = Color.Yellow
    /*primary = primaryColor,
    primaryVariant = primaryDarkColor,
    secondary = secondaryColor,
    secondaryVariant = secondaryDarkColor,
    surface = surfaceColor,
    error = errorColor,
    onPrimary = onPrimaryColor,
    onSecondary = onSecondaryColor,
    onBackground = onBackgroundColor,
    onSurface = onSurfaceColor,
    onError = onErrorColor*/
)

val darkColorsPalette = darkColorScheme(
    primary = ColorPrimaryDark,
    secondary = ColorSecondary,
    background = Color.Black,
    surface = Color.White,
    tertiary = Color.White,
    outline = Color.Yellow
    /*primary = primaryColor,
    primaryVariant = primaryDarkColor,
    secondary = secondaryColor,
    secondaryVariant = secondaryDarkColor,
    error = errorColor,
    onPrimary = onPrimaryColor,
    onSecondary = onSecondaryColor,
    onBackground = onBackgroundColor,
    onSurface = onSurfaceColor,
    onError = onErrorColor*/
)