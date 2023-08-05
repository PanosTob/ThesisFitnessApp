package gr.dipae.thesisfitnessapp.ui.app.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton

data class AppUiState(
    val navigateToWizard: MutableState<Boolean> = mutableStateOf(false),
    val navigateToLobby: MutableState<Boolean> = mutableStateOf(false),
    val navigateBackToLogin: MutableState<Boolean> = mutableStateOf(false)
)

@Singleton
class PoDHelper @Inject constructor() {
    var isAlreadyCreated = false

    fun initPoDHelper() {
        isAlreadyCreated = true
    }
}