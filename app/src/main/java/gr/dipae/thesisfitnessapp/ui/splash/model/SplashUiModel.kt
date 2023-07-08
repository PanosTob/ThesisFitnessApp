package gr.dipae.thesisfitnessapp.ui.splash.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SplashUiState(
    val navigateToRoute: MutableState<String?> = mutableStateOf(null)
)