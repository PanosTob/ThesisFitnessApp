package gr.dipae.thesisfitnessapp.ui.welcome.viewmodel

import android.content.IntentSender
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.livedata.SingleLiveEvent
import gr.dipae.thesisfitnessapp.ui.welcome.model.LoginUiState
import gr.dipae.thesisfitnessapp.usecase.user.GetGoogleSignInIntentUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getGoogleSignInIntentUseCase: GetGoogleSignInIntentUseCase
) : BaseViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    private val _initiateGoogleSignInWindow = SingleLiveEvent<IntentSender>()
    val initiateGoogleSignInWindow: LiveData<IntentSender> = _initiateGoogleSignInWindow

    fun init() {
    }

    fun onGoogleSignInClicked(webClientId: String) {
        launchWithProgress {
            getGoogleSignInIntentUseCase(webClientId)?.pendingIntent?.intentSender?.let {
                _initiateGoogleSignInWindow.value = it
            }
        }
    }

}