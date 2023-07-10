package gr.dipae.thesisfitnessapp.ui.app

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.session.SessionHandler
import gr.dipae.thesisfitnessapp.domain.user.login.SignInResult
import gr.dipae.thesisfitnessapp.domain.user.logout.LogoutResult
import gr.dipae.thesisfitnessapp.ui.app.model.AppUiState
import gr.dipae.thesisfitnessapp.ui.app.model.PoDHelper
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.diet.navigation.DietRoute
import gr.dipae.thesisfitnessapp.ui.livedata.NetworkLiveData
import gr.dipae.thesisfitnessapp.ui.livedata.SingleLiveEvent
import gr.dipae.thesisfitnessapp.ui.lobby.navigation.LobbyRoute
import gr.dipae.thesisfitnessapp.ui.lobby.viewmodel.LobbyViewModel
import gr.dipae.thesisfitnessapp.ui.profile.navigation.ProfileRoute
import gr.dipae.thesisfitnessapp.ui.sport.navigation.SportsRoute
import gr.dipae.thesisfitnessapp.ui.theme.ColorPrimary
import gr.dipae.thesisfitnessapp.ui.workout.navigation.WorkoutRoute
import gr.dipae.thesisfitnessapp.usecase.user.DisableGoogleSignIfUserDenialsExceedsLimitUseCase
import gr.dipae.thesisfitnessapp.usecase.user.LogoutUserUseCase
import gr.dipae.thesisfitnessapp.usecase.user.SignInUserUseCase
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
    private val disableGoogleSignIfUserDenialsExceedsLimitUseCase: DisableGoogleSignIfUserDenialsExceedsLimitUseCase
) : BaseViewModel() {

    private val _navigateId = SingleLiveEvent<Triple<Int, Bundle?, NavOptions?>>()
    val navigateId: LiveData<Triple<Int, Bundle?, NavOptions?>> = _navigateId

    private val _restartApp = SingleLiveEvent<Unit>()
    val restartApp: LiveData<Unit> = _restartApp

    private val _recreateApp = SingleLiveEvent<Unit>()
    val recreateApp: LiveData<Unit> = _recreateApp

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

            _appUiState.value.navigateToWizard.value = true
        }
    }

    fun showLoggedInUi() {
        _appUiState.value.apply {
            colorScreen.value = ColorPrimary
            topBarState.isVisible.value = true
            bottomAppBarItems.value = LobbyViewModel.bottomAppBarUiItems
        }
    }

    fun updateToCalenderIcon() {
        _appUiState.value.topBarState.actionIcon.value = R.drawable.ic_calendar
    }

    fun hideScafoldBars() {
        _appUiState.value.apply {
            colorScreen.value = Color.Black
            topBarState.isVisible.value = false
            bottomAppBarItems.value = emptyList()
        }
    }

    fun updateTopBarTitle(route: String) {
        _appUiState.value.topBarState.titleRes.value = routeTitles[route] ?: R.string.empty
    }

    fun logoutUser() {
        launchWithProgress {
            val logoutResult = logoutUserUseCase()
            if (logoutResult is LogoutResult.Success) {
                _appUiState.value.navigateBackToLogin.value = true
                hideScafoldBars()
            }
        }
    }

    fun recreateApp() {
        launch {
            delay(200)
            _recreateApp.value = Unit
        }
    }

    companion object {
        val routeTitles: LinkedHashMap<String, Int> = linkedMapOf(
            LobbyRoute to R.string.home_title,
            WorkoutRoute to R.string.workout_title,
            SportsRoute to R.string.sport_title,
            DietRoute to R.string.diet_title,
            ProfileRoute to R.string.profile_title
        )
    }
}