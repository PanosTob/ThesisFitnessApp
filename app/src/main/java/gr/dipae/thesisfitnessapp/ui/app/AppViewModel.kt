package gr.dipae.thesisfitnessapp.ui.app

import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.session.SessionHandler
import gr.dipae.thesisfitnessapp.domain.user.login.SignInResult
import gr.dipae.thesisfitnessapp.domain.user.logout.LogoutResult
import gr.dipae.thesisfitnessapp.ui.app.model.AppUiState
import gr.dipae.thesisfitnessapp.ui.app.model.PoDHelper
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.livedata.NetworkLiveData
import gr.dipae.thesisfitnessapp.ui.livedata.SingleLiveEvent
import gr.dipae.thesisfitnessapp.usecase.app.UpdateStepCounterUseCase
import gr.dipae.thesisfitnessapp.usecase.login.DisableGoogleSignIfUserDenialsExceedsLimitUseCase
import gr.dipae.thesisfitnessapp.usecase.login.LogoutUserUseCase
import gr.dipae.thesisfitnessapp.usecase.login.SignInUserUseCase
import gr.dipae.thesisfitnessapp.util.SAVE_STATE_APP
import gr.dipae.thesisfitnessapp.util.base.UserDeclinedException
import gr.dipae.thesisfitnessapp.util.delegate.SaveStateDelegate
import gr.dipae.thesisfitnessapp.util.ext.handleFirebaseException
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val networkLiveData: NetworkLiveData,
    private val sessionHandler: SessionHandler,
    private val podHelper: PoDHelper,
    private val signInUserUseCase: SignInUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val disableGoogleSignIfUserDenialsExceedsLimitUseCase: DisableGoogleSignIfUserDenialsExceedsLimitUseCase,
    private val updateStepCounterUseCase: UpdateStepCounterUseCase
) : BaseViewModel() {

    private val _restartApp = SingleLiveEvent<Unit>()
    val restartApp: LiveData<Unit> = _restartApp

    private val _recreateApp = SingleLiveEvent<Unit>()
    val recreateApp: LiveData<Unit> = _recreateApp

    private val _startStopWatch = SingleLiveEvent<Unit>()
    val startStopWatch: LiveData<Unit> = _startStopWatch

    private val _pauseStopWatch = SingleLiveEvent<Unit>()
    val pauseStopWatch: LiveData<Unit> = _pauseStopWatch

    private val _stopStopWatch = SingleLiveEvent<Unit>()
    val stopStopWatch: LiveData<Unit> = _stopStopWatch

    private val _submitLanguageChange = MutableLiveData<Unit>()
    val submitLanguageChange: LiveData<Unit> = _submitLanguageChange

    private val _initiateGoogleSignInWindow = SingleLiveEvent<IntentSender>()
    val initiateGoogleSignInWindow: LiveData<IntentSender> = _initiateGoogleSignInWindow

    private val _appUiState: MutableState<AppUiState> = mutableStateOf(AppUiState())
    val appUiState: State<AppUiState> = _appUiState

    private val _initApp = SingleLiveEvent<Boolean>()

    private var isAlreadyCreated by SaveStateDelegate<Boolean>(savedStateHandle = savedStateHandle, key = SAVE_STATE_APP)

    fun initApp() {
        // catch PoD & DkA cases - restart app
        val processOfDeathCase = isAlreadyCreated == true && !podHelper.isAlreadyCreated
        val dontKeepActivitiesCase = isAlreadyCreated == true && podHelper.isAlreadyCreated && _initApp.value == null
        if (processOfDeathCase || dontKeepActivitiesCase) {
            _restartApp.value = Unit
        }

        isAlreadyCreated = true
        _initApp.value = true
        podHelper.initPoDHelper()
    }

    fun onGoogleSignInClicked(intent: IntentSender) {
        _initiateGoogleSignInWindow.value = intent
    }

    fun signInUser(googleSignInData: Intent?, resultCode: Int) {
        launchWithProgress {
            val signInResponse = signInUserUseCase(googleSignInData, resultCode)
            if (signInResponse is SignInResult.FirebaseFailure) {
                signInResponse.firebaseException.handleFirebaseException()
                return@launchWithProgress
            }
            if (signInResponse is SignInResult.Failure) {
                if (signInResponse.exception is UserDeclinedException) {
                    disableGoogleSignIfUserDenialsExceedsLimitUseCase()
                }
                return@launchWithProgress
            }

            if (signInResponse is SignInResult.AlreadyRegistered) {
                _appUiState.value.navigateToLobby.value = true
                return@launchWithProgress
            }
            _appUiState.value.navigateToWizard.value = true
        }
    }

    fun updateStepCounter(newStepCounterValue: Long) {
        launch {
            updateStepCounterUseCase(newStepCounterValue)
        }
    }

    fun startStopWatch() {
        _startStopWatch.value = Unit
    }

    fun pauseStopWatch() {
        _pauseStopWatch.value = Unit
    }

    fun stopStopWatch() {
        _stopStopWatch.value = Unit
    }

    fun logoutUser() {
        launchWithProgress {
            val logoutResult = logoutUserUseCase()
            if (logoutResult is LogoutResult.Success) {
                _appUiState.value.navigateBackToLogin.value = true
            }
        }
    }

    fun recreateApp() {
        launch {
            delay(200)
            _recreateApp.value = Unit
        }
    }
}