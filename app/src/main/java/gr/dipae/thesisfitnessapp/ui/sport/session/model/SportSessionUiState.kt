package gr.dipae.thesisfitnessapp.ui.sport.session.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SportSessionUiState(
    val timerValue: MutableState<String> = mutableStateOf("")
)