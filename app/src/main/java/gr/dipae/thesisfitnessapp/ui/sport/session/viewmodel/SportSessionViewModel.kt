package gr.dipae.thesisfitnessapp.ui.sport.session.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.sport.session.mapper.SportSessionUiMapper
import gr.dipae.thesisfitnessapp.ui.sport.session.model.SportSessionUiState
import gr.dipae.thesisfitnessapp.ui.sport.session.navigation.SportSessionArgumentKeys
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportByIdUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.GetSportParameterNavigationArgumentUseCase
import gr.dipae.thesisfitnessapp.usecase.sports.SetSportSessionUseCase
import gr.dipae.thesisfitnessapp.util.timer.MyCountUpTimer
import javax.inject.Inject

@HiltViewModel
class SportSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSportByIdUseCase: GetSportByIdUseCase,
    private val getSportParameterNavigationArgumentUseCase: GetSportParameterNavigationArgumentUseCase,
    private val setSportSessionUseCase: SetSportSessionUseCase,
    private val sportSessionUiMapper: SportSessionUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<SportSessionUiState?> = mutableStateOf(null)
    val uiState: State<SportSessionUiState?> = _uiState

    private val sportId = savedStateHandle.get<String>(SportSessionArgumentKeys[0])!!
    private val sportParameter = savedStateHandle.get<String>(SportSessionArgumentKeys[1])!!

    private val mainTimer by lazy {
        //TODO IMPLEMENT ANOTHER COUNT UP TIMER
        object : MyCountUpTimer(100) {
            override fun onTick(nowTime: Long) {
                _uiState.value?.mainTimerValue?.value = sportSessionUiMapper(nowTime)
            }
        }
    }
    private val breakTimer by lazy {
        //TODO IMPLEMENT ANOTHER COUNT UP TIMER
        object : MyCountUpTimer(100) {
            override fun onTick(nowTime: Long) {
                _uiState.value?.breakTimerValue?.value = sportSessionUiMapper(nowTime)
            }
        }
    }

    override fun onCleared() {
        cancelMainTimer()
        cancelBreakTimer()
        super.onCleared()
    }

    fun init() {
        launchWithProgress {
            getSportByIdUseCase(sportId)?.let {
                _uiState.value = sportSessionUiMapper(sportId, it.parameters, getSportParameterNavigationArgumentUseCase(sportParameter))

                initMainTimer()
            }
        }
    }

    fun onStartClicked() {
        pauseBreakTimer()
        initMainTimer()
    }

    fun onPauseClicked() {
        pauseMainTimer()
        initBreakTimer()
    }

    fun onSessionFinish() {
        launchWithProgress {
            _uiState.value?.apply {
                setSportSessionUseCase(sportId, parameters, parameterToBeAchieved)
                backToLogin.value = true
            }
        }
    }

    private fun initMainTimer() {
        if (!mainTimer.isActive) {
            mainTimer.start()
        }
    }

    private fun pauseMainTimer() {

    }

    private fun initBreakTimer() {
        if (!mainTimer.isActive) {
            mainTimer.start()
        }
    }

    private fun pauseBreakTimer() {

    }

    private fun cancelBreakTimer() {
        breakTimer.cancel()
    }

    private fun cancelMainTimer() {
        mainTimer.cancel()
    }
}