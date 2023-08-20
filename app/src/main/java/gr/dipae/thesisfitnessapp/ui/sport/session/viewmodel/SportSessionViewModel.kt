package gr.dipae.thesisfitnessapp.ui.sport.session.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportSessionSaveResult
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.sport.session.mapper.SportSessionUiMapper
import gr.dipae.thesisfitnessapp.ui.sport.session.model.ContinuationState
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.SportSessionArgumentKeys
import gr.dipae.thesisfitnessapp.usecase.sport.CalculateTravelledDistanceMetersUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.CalculateUserPaceUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportParameterNavigationArgumentUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportSessionBreakTimerDurationLiveUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportSessionDistanceLiveUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetSportSessionDurationLiveUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetUserLocationUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetUserMapRouteUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.GetUserPreviousLocationUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.PauseSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.SetSportSessionDistanceUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.SetSportSessionUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.SetUserPreviousLocationUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.StartSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.usecase.sport.StopSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.util.ext.toDoubleWithSpecificDecimals
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SportSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSportParameterNavigationArgumentUseCase: GetSportParameterNavigationArgumentUseCase,
    private val setSportSessionUseCase: SetSportSessionUseCase,
    private val getUserPreviousLocationUseCase: GetUserPreviousLocationUseCase,
    private val setUserPreviousLocationUseCase: SetUserPreviousLocationUseCase,
    private val setSportSessionDistanceUseCase: SetSportSessionDistanceUseCase,
    private val calculateTravelledDistanceMetersUseCase: CalculateTravelledDistanceMetersUseCase,
    private val calculateUserPaceUseCase: CalculateUserPaceUseCase,
    private val getSportSessionDistanceLiveUseCase: GetSportSessionDistanceLiveUseCase,
    private val getUserMapRouteUseCase: GetUserMapRouteUseCase,
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

    private var sportDuration = 0L
    override fun onCleared() {
        stopSportSessionBreakTimerUseCase()
        _uiState.value?.backToSports?.value = true
        super.onCleared()
    }

    fun init() {
        launchWithProgress {
            _uiState.value = sportSessionUiMapper(sportParameter, sportHasMap)
        }
    }

    private fun initTimers() {
        jobOfCollectingDuration = launch {
            getSportSessionDurationLiveUseCase().collectLatest {
                _uiState.value?.duration?.value = sportSessionUiMapper.toHundredsOfASecond(it)
            }
        }

        jobOfCollectingBreak = launch {
            getSportSessionBreakTimerDurationLiveUseCase().collectLatest {
                _uiState.value?.breakTime?.value = sportSessionUiMapper.toSecondsString(it)
            }
        }
    }

    fun onLocationPermissionsAccepted() {
        _uiState.value?.playStateBtn?.isEnabled?.value = true
    }

    fun onStopSession() {
        sportId?.apply {
            jobOfCollectingDuration?.cancel()
            jobOfCollectingBreak?.cancel()
            jobOfCollectionUserLocation?.cancel()
            _uiState.value?.playStateBtn?.apply {
                isEnabled.value = false
                iconRes.value = R.drawable.ic_play
            }
            _uiState.value?.stopBtnEnabled?.value = false
            _uiState.value?.mapState?.userRoute?.value = getUserMapRouteUseCase()
        }
    }

    fun onStartAnimationComplete() {
        showContent()
        initTimers()
    }

    private fun showContent() {
        if (sportHasMap == true) {
            subscribeToLocationUpdates()
        }
        _uiState.value?.showContent?.value = true
        _uiState.value?.playStateBtn?.iconRes?.value = R.drawable.ic_pause
        _uiState.value?.stopBtnEnabled?.value = true
    }

    fun subscribeToLocationUpdates() {
        _uiState.value?.apply {
            jobOfCollectionUserLocation = launch {
                getUserLocationUseCase().collectLatest {
                    mapState.userLocation.value = it
                    handleDistanceAndPace(it)

                    setUserPreviousLocationUseCase(it)
                }
            }
        }
    }

    private suspend fun handleDistanceAndPace(userLocation: LatLng) {
        _uiState.value?.apply {
            val travelledDistance = calculateTravelledDistanceMetersUseCase(getUserPreviousLocationUseCase(), userLocation, getSportSessionDistanceLiveUseCase().value)
            val distanceTwoDecimals = (travelledDistance.toDouble() / 1000).toDoubleWithSpecificDecimals(2)
            distance.value = "${if (distanceTwoDecimals == 0.0) 0 else distanceTwoDecimals} km/h"

            setSportSessionDistanceUseCase(travelledDistance)
            sportDuration = getSportSessionDurationLiveUseCase().value
            pace.value = "${calculateUserPaceUseCase(sportDuration, distanceTwoDecimals)} min/km"
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
        jobOfCollectionUserLocation?.cancel()
    }

    fun pauseBreakTimer() {
        pauseSportSessionBreakTimerUseCase()
        _uiState.value?.playStateBtn?.apply {
            timerState.value = ContinuationState.Running
            iconRes.value = R.drawable.ic_pause
        }
        subscribeToLocationUpdates()
    }

    fun onSessionFinish() {
        launchWithProgress {
            sportId?.apply {
                val distance = getSportSessionDistanceLiveUseCase().value
                val breakTime = getSportSessionBreakTimerDurationLiveUseCase().value

                jobOfCollectingDuration?.cancel()
                jobOfCollectingBreak?.cancel()
                jobOfCollectionUserLocation?.cancel()

                val response = setSportSessionUseCase(sportId, sportParameter, sportDuration, distance, breakTime)
                if (response is SportSessionSaveResult.Success) {
                    _uiState.value?.backToSports?.value = true
                }
            }
        }
    }
}