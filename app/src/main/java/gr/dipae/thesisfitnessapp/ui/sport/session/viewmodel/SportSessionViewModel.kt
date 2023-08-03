package gr.dipae.thesisfitnessapp.ui.sport.session.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportSessionSaveResult
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.sport.session.mapper.SportSessionUiMapper
import gr.dipae.thesisfitnessapp.ui.sport.session.model.ContinuationState
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.SportSessionArgumentKeys
import gr.dipae.thesisfitnessapp.usecase.sport.CalculateTravelledDistanceUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportByIdUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportParameterNavigationArgumentUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportSessionBreakTimerDurationLiveUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportSessionDurationLiveUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetUserLocationUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetUserPreviousLocationUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.PauseSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.SetSportSessionDistanceUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.SetSportSessionUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.StartSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.StartUserLocationUpdatesUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.StopSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.StopUserLocationUpdatesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SportSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSportParameterNavigationArgumentUseCase: GetSportParameterNavigationArgumentUseCase,
    private val getSportByIdUseCase: GetSportByIdUseCase,
    private val setSportSessionUseCase: SetSportSessionUseCase,
    private val getUserPreviousLocationUseCase: GetUserPreviousLocationUseCase,
    private val setSportSessionDistanceUseCase: SetSportSessionDistanceUseCase,
    private val calculateTravelledDistanceUseCase: CalculateTravelledDistanceUseCase,
    private val startUserLocationUpdatesUseCase: StartUserLocationUpdatesUseCase,
    private val stopUserLocationUpdatesUseCase: StopUserLocationUpdatesUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getSportSessionDurationLiveUseCase: GetSportSessionDurationLiveUseCase,
    private val getSportSessionBreakTimerDurationLiveUseCase: GetSportSessionBreakTimerDurationLiveUseCase,
    private val startSportSessionBreakTimerUseCase: StartSportSessionBreakTimerUseCase,
    private val pauseSportSessionBreakTimerUseCase: PauseSportSessionBreakTimerUseCase,
    private val stopSportSessionBreakTimerUseCase: StopSportSessionBreakTimerUseCase,
    private val sportSessionUiMapper: SportSessionUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<SportSessionUiState?> = mutableStateOf(null)
    val uiState: State<SportSessionUiState?> = _uiState

    private val sportId = savedStateHandle.get<String>(SportSessionArgumentKeys[0])
    private val sportHasMap = savedStateHandle.get<Boolean>(SportSessionArgumentKeys[1])
    private val sportParameter = getSportParameterNavigationArgumentUseCase(savedStateHandle[SportSessionArgumentKeys[2]])

    private var jobOfCollectingDuration: Job? = null
    private var jobOfCollectingBreak: Job? = null
    private var jobOfCollectionUserLocation: Job? = null
    override fun onCleared() {
        stopSportSessionBreakTimerUseCase()
        stopUserLocationUpdatesUseCase()
        super.onCleared()
    }

    fun init() {
        launchWithProgress {
            getSportByIdUseCase(sportId)?.let {
                _uiState.value = sportSessionUiMapper(sportParameter, sportHasMap)
            }
        }
    }

    private fun initTimers() {
        launch {
            jobOfCollectingDuration = getSportSessionDurationLiveUseCase().onEach {
                _uiState.value?.mainTimerValue?.value = sportSessionUiMapper.toHundredsOfASecond(it)
            }.launchIn(viewModelScope)
        }

        launch {
            jobOfCollectingBreak = getSportSessionBreakTimerDurationLiveUseCase().onEach {
                _uiState.value?.breakTimerValue?.value = sportSessionUiMapper.toSecondsString(it)
            }.launchIn(viewModelScope)
        }
    }

    fun onLocationPermissionsAccepted() {
        _uiState.value?.playStateBtn?.isEnabled?.value = true
    }

    fun onStartAnimationComplete() {
        showContent()
        initTimers()
    }

    private fun showContent() {
        _uiState.value?.showContent?.value = true
        _uiState.value?.playStateBtn?.iconRes?.value = R.drawable.ic_pause
    }

    fun onMapLoaded() {
        _uiState.value?.apply {
            launch {
                jobOfCollectionUserLocation = getUserLocationUseCase().onEach {
                    val distance = calculateTravelledDistanceUseCase(getUserPreviousLocationUseCase(), it)
                    setSportSessionDistanceUseCase(distance)
                    mapState.userLocation.value = it
                }.launchIn(viewModelScope)
            }
            startUserLocationUpdatesUseCase()
        }
    }

    fun onMyLocationButtonClick() {

    }

    fun startBreakTimer() {
        startSportSessionBreakTimerUseCase()
        _uiState.value?.playStateBtn?.apply {
            timerState.value = ContinuationState.Stopped
            iconRes.value = R.drawable.ic_play
        }
    }

    fun pauseBreakTimer() {
        pauseSportSessionBreakTimerUseCase()
        _uiState.value?.playStateBtn?.apply {
            timerState.value = ContinuationState.Running
            iconRes.value = R.drawable.ic_pause
        }
    }

    fun onSessionFinish() {
        launchWithProgress {
            sportId?.apply {
                jobOfCollectingDuration?.cancel()
                jobOfCollectingBreak?.cancel()

                val response = setSportSessionUseCase(sportId, sportParameter)
                if (response is SportSessionSaveResult.Success) {
                    _uiState.value?.backToLogin?.value = true
                }
            }
        }
    }
}