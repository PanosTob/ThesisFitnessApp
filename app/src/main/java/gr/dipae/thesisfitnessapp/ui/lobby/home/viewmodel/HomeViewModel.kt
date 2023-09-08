package gr.dipae.thesisfitnessapp.ui.lobby.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.lobby.home.mapper.HomeUiMapper
import gr.dipae.thesisfitnessapp.ui.lobby.home.model.HomeUiState
import gr.dipae.thesisfitnessapp.usecase.app.GetStepCounterUseCase
import gr.dipae.thesisfitnessapp.usecase.app.GetUserIsRunningStatusUseCase
import gr.dipae.thesisfitnessapp.usecase.history.CheckExistenceAndCreateTodaysSummaryUseCase
import gr.dipae.thesisfitnessapp.usecase.user.GetUserDetailsUseCase
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getStepCounterUseCase: GetStepCounterUseCase,
    private val getUserIsRunningStatusUseCase: GetUserIsRunningStatusUseCase,
    private val checkExistenceAndCreateTodaysSummaryUseCase: CheckExistenceAndCreateTodaysSummaryUseCase,
    private val homeUiMapper: HomeUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<HomeUiState?> = mutableStateOf(null)
    val uiState: State<HomeUiState?> = _uiState

    private var userIsRunning: Boolean = false

    private var user: User? = null
    private var daySummary: DaySummary? = null
    fun init() {
        launchWithProgress {
            daySummary = checkExistenceAndCreateTodaysSummaryUseCase()
            user = getUserDetailsUseCase()
            _uiState.value = homeUiMapper(user, daySummary)
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
            userMovementTracking.stepsTracking.fulfillmentPercentage.value += (stepsDone.toFloat() / userDetails.dailyStepGoal).coerceAtMost(1f)
        }
    }

    private fun handleCaloriesTracking(stepsDone: Long) {
        _uiState.value?.apply {
            val bodyWeight = user?.bodyWeight ?: return@apply

            val caloriesBurned = calculateCaloricBurnOfOneStep(stepsDone, bodyWeight)
            userMovementTracking.caloriesTracking.remaining.value = (userDetails.dailyCaloricBurnGoal - caloriesBurned).toInt().toString()
            userMovementTracking.caloriesTracking.fulfillmentPercentage.value += (caloriesBurned.toFloat() / userDetails.dailyCaloricBurnGoal).coerceAtMost(1f)
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