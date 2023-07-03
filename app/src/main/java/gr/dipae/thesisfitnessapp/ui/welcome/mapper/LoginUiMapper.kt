package gr.dipae.thesisfitnessapp.ui.welcome.mapper

import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.ui.welcome.model.LoginUiState
import javax.inject.Inject

class LoginUiMapper @Inject constructor() : Mapper {

    operator fun invoke(googleSignInBtnEnabled: Boolean): LoginUiState {
        return LoginUiState(
            googleSignInButtonEnabledState = mutableStateOf(googleSignInBtnEnabled)
        )
    }
}