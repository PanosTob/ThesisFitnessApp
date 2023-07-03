package gr.dipae.thesisfitnessapp.ui.welcome.viewmodel

import android.content.IntentSender
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.data.user.login.broadcast.LoginBroadcast
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.livedata.SingleLiveEvent
import gr.dipae.thesisfitnessapp.ui.welcome.mapper.LoginUiMapper
import gr.dipae.thesisfitnessapp.ui.welcome.model.LoginUiState
import gr.dipae.thesisfitnessapp.usecase.user.GetGoogleSignInIntentUseCase
import gr.dipae.thesisfitnessapp.usecase.user.isGoogleSignInBlockedUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUiMapper: LoginUiMapper,
    private val loginBroadcast: LoginBroadcast,
    private val getGoogleSignInIntentUseCase: GetGoogleSignInIntentUseCase,
    private val isGoogleSignInBlockedUseCase: isGoogleSignInBlockedUseCase
) : BaseViewModel() {

    private val _uiState: MutableState<LoginUiState?> = mutableStateOf(null)
    val uiState: State<LoginUiState?> = _uiState

    private val _initiateGoogleSignInWindow = SingleLiveEvent<IntentSender>()
    val initiateGoogleSignInWindow: LiveData<IntentSender> = _initiateGoogleSignInWindow

    private var googleSignInBlockageJob: Job? = null

    init {
        launch {
            val googleSignInBtnEnabled = !isGoogleSignInBlockedUseCase()
            _uiState.value = loginUiMapper(googleSignInBtnEnabled)
        }
        launch {
            loginBroadcast.googleSignInEnabledState.collectLatest { enabled ->
                enabled?.let {
                    if (it) {
                        cancelCheckGoogleSignInBlockageJob()
                    }
                    _uiState.value?.googleSignInButtonEnabledState?.value = it
                }
            }
        }
        initGoogleSignInBlockageJob()
    }

    private fun cancelCheckGoogleSignInBlockageJob() {
        if (googleSignInBlockageJob?.isActive == true) {
            googleSignInBlockageJob?.cancel()
        }
    }

    private fun initGoogleSignInBlockageJob() {
        googleSignInBlockageJob = viewModelScope.launch {
            delay(1000)
            isGoogleSignInBlockedUseCase()
        }
    }

    fun onGoogleSignInClicked() {
        launch {
            getGoogleSignInIntentUseCase()?.let {
                _initiateGoogleSignInWindow.value = it
            }
        }
    }

}