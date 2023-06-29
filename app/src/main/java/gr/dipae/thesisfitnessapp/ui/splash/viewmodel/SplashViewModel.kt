package gr.dipae.thesisfitnessapp.ui.splash.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.splash.model.SplashUiState
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    private val _uiState = mutableStateOf(SplashUiState())
    val uiState: State<SplashUiState> = _uiState

    fun init() {
        launch {
            delay(2000)
            _uiState.value.navigateToWelcome.value = true
        }
    }
}