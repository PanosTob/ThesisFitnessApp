package gr.dipae.thesisfitnessapp.ui.welcome.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class WelcomeUiState(
    val navigateToHome: MutableState<Boolean> = mutableStateOf(false)
)