package gr.dipae.thesisfitnessapp.ui.splash.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.LobbyRoute
import gr.dipae.thesisfitnessapp.ui.splash.model.SplashUiState
import gr.dipae.thesisfitnessapp.ui.welcome.navigation.LoginRoute
import gr.dipae.thesisfitnessapp.usecase.login.isUserSignedInUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserSignedInUseCase: isUserSignedInUseCase
) : BaseViewModel() {

    private val _uiState = mutableStateOf(SplashUiState())
    val uiState: State<SplashUiState> = _uiState

    fun init() {
        launch {
            delay(2000)
            if (isUserSignedInUseCase()) {
                _uiState.value.navigateToRoute.value = LobbyRoute
            } else {
                _uiState.value.navigateToRoute.value = LoginRoute
            }
        }
    }
}