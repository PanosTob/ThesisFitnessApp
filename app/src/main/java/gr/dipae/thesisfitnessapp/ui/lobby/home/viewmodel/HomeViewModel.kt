package gr.dipae.thesisfitnessapp.ui.lobby.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.lobby.home.mapper.HomeUiMapper
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.usecase.app.GetStepCounterUseCase
import gr.dipae.thesisfitnessapp.usecase.app.GetUserIsRunningStatusUseCase
import gr.dipae.thesisfitnessapp.usecase.user.GetUserDetailsUseCase
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getStepCounterUseCase: GetStepCounterUseCase,
    private val getUserIsRunningStatusUseCase: GetUserIsRunningStatusUseCase,
    private val homeUiMapper: HomeUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<HomeUiState?> = mutableStateOf(null)
    val uiState: State<HomeUiState?> = _uiState

    private var userIsRunning: Boolean = false
    fun init() {
        launchWithProgress {
            _uiState.value = homeUiMapper(getUserDetailsUseCase())
        }
        launch {
            getStepCounterUseCase().collectLatest {
                _uiState.value?.apply {
                    userActivityTracking.stepCounter.value = it.toString()
                    userDetails.bodyWeight.toDoubleOrNull()?.let { bodyWeight ->
                        userActivityTracking.caloricBurn.value += calculateCaloricBurn(bodyWeight)
                    }
                }
            }
        }
        launch {
            getUserIsRunningStatusUseCase().collectLatest {
                userIsRunning = it
            }
        }
    }

    private fun calculateCaloricBurn(bodyWeight: Double): Float {
        return bodyWeight.toFloat() * if (userIsRunning) 0.4f else 0.2f * 76.2f / 100000.0f
    }
}