package gr.dipae.thesisfitnessapp.ui.sport.session.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter

data class SportSessionUiState(
    val sportId: String,
    val parameters: List<SportParameter>,
    val parameterToBeAchieved: SportParameter,
    val mainTimerValue: MutableState<String> = mutableStateOf(""),
    val breakTimerValue: MutableState<String> = mutableStateOf(""),
    val resumePauseToggleBtn: MutableState<ContinuationState> = mutableStateOf(ContinuationState.PauseState),
    val playPauseIcon: MutableState<Int> = mutableStateOf(R.drawable.ic_pause),
    val backToLogin: MutableState<Boolean> = mutableStateOf(false)
)

sealed class ContinuationState {
    object ResumeState : ContinuationState()
    object PauseState : ContinuationState()
}