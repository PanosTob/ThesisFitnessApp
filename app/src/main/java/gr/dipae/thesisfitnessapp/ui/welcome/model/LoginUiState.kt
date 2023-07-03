package gr.dipae.thesisfitnessapp.ui.welcome.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LoginUiState(
    val googleSignInButtonEnabledState: MutableState<Boolean>,
    val navigateToHome: MutableState<Boolean> = mutableStateOf(false)
)