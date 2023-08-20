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
                    handleStepsTracking(it)
                    handleCaloriesTracking(it)
                }
            }
        }
        launch {
            getUserIsRunningStatusUseCase().collectLatest {
                userIsRunning = it
            }
        }
    }

    private fun handleStepsTracking(stepsDone: Long) {
        _uiState.value?.apply {
            userMovementTracking.stepsTracking.remaining.value = (userDetails.dailyStepGoal - stepsDone).toString()
            userMovementTracking.stepsTracking.fulfillmentPercentage.value = (stepsDone / userDetails.dailyStepGoal).toFloat()
        }
    }

    private fun handleCaloriesTracking(stepsDone: Long) {
        _uiState.value?.apply {
            val bodyWeight = userDetails.bodyWeight.toDoubleOrNull() ?: return

            val caloriesBurned = calculateCaloricBurnOfOneStep(stepsDone, bodyWeight)
            userMovementTracking.caloriesTracking.remaining.value = (userDetails.dailyCaloricBurnGoal - caloriesBurned).toString()
            userMovementTracking.caloriesTracking.fulfillmentPercentage.value += (caloriesBurned / userDetails.dailyCaloricBurnGoal).toFloat()
        }
    }

    private fun calculateCaloricBurnOfOneStep(stepsDone: Long, bodyWeight: Double): Double {
        return stepsDone * (bodyWeight * if (userIsRunning) METRIC_RUNNING_FACTOR else METRIC_WALKING_FACTOR * AVERAGE_STEP_LENGTH / TEN_THOUSAND_STEPS)
    }

    companion object {
        const val METRIC_RUNNING_FACTOR = 1.02784823
        const val METRIC_WALKING_FACTOR = 0.708
        const val AVERAGE_STEP_LENGTH = 76.2
        const val TEN_THOUSAND_STEPS = 100000
    }
}