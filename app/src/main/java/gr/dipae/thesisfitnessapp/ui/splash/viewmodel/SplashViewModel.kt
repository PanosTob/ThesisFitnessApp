package gr.dipae.thesisfitnessapp.ui.splash.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.splash.model.SplashUiState
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    private val _uiState = mutableStateOf<SplashUiState?>(null)
    val uiState: State<SplashUiState?> = _uiState

    fun init() {
        launch {
            _uiState.value = SplashUiState(img = R.drawable.app_logo)
        }
    }
}