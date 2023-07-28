package gr.dipae.thesisfitnessapp.ui.sport.session.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.sport.session.mapper.SportSessionUiMapper
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.SportSessionArgumentKeys
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportByIdUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportParameterNavigationArgumentUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportSessionBreakTimerDurationLiveUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportSessionDurationLiveUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.PauseSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.SetSportSessionUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.StartSportSessionBreakTimerUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.StopSportSessionBreakTimerUseCase
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SportSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSportByIdUseCase: GetSportByIdUseCase,
    private val getSportParameterNavigationArgumentUseCase: GetSportParameterNavigationArgumentUseCase,
    private val setSportSessionUseCase: SetSportSessionUseCase,
    private val getSportSessionDurationLiveUseCase: GetSportSessionDurationLiveUseCase,
    private val getSportSessionBreakTimerDurationLiveUseCase: GetSportSessionBreakTimerDurationLiveUseCase,
    private val startSportSessionBreakTimerUseCase: StartSportSessionBreakTimerUseCase,
    private val pauseSportSessionBreakTimerUseCase: PauseSportSessionBreakTimerUseCase,
    private val stopSportSessionBreakTimerUseCase: StopSportSessionBreakTimerUseCase,
    private val sportSessionUiMapper: SportSessionUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<SportSessionUiState?> = mutableStateOf(null)
    val uiState: State<SportSessionUiState?> = _uiState

    private val sportId = savedStateHandle.get<String>(SportSessionArgumentKeys[0])!!
    private val sportParameter = savedStateHandle.get<String>(SportSessionArgumentKeys[1])!!

    override fun onCleared() {
        stopSportSessionBreakTimerUseCase()
        super.onCleared()
    }
    fun init() {
        launchWithProgress {
            getSportByIdUseCase(sportId)?.let {
                _uiState.value = sportSessionUiMapper(sportId, it.parameters, getSportParameterNavigationArgumentUseCase(sportParameter))
            }
        }
        launch {
            getSportSessionDurationLiveUseCase().collectLatest {
                _uiState.value?.mainTimerValue?.value = sportSessionUiMapper(it)
            }
        }

        launch {
            getSportSessionBreakTimerDurationLiveUseCase().collectLatest {
                _uiState.value?.breakTimerValue?.value = sportSessionUiMapper(it)
            }
        }
    }

    fun startBreakTimer() {
        startSportSessionBreakTimerUseCase()
        _uiState.value?.playPauseIcon?.value = R.drawable.ic_play
    }

    fun pauseBreakTimer() {
        pauseSportSessionBreakTimerUseCase()
        _uiState.value?.playPauseIcon?.value = R.drawable.ic_pause
    }

    fun onSessionFinish() {
        launchWithProgress {
            _uiState.value?.apply {
                setSportSessionUseCase(sportId, parameters, parameterToBeAchieved)
                backToLogin.value = true
            }
        }
    }
}