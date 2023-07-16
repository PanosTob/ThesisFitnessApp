package gr.dipae.thesisfitnessapp.ui.workout.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.dipae.thesisfitnessapp.ui.base.BaseViewModel
import gr.dipae.thesisfitnessapp.ui.workout.mapper.WorkoutUiMapper
import gr.dipae.thesisfitnessapp.ui.workout.model.WorkoutsUiState
import gr.dipae.thesisfitnessapp.usecase.workout.GetWorkoutUseCase
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val workoutUiMapper: WorkoutUiMapper
) : BaseViewModel() {

    private val _uiState: MutableState<WorkoutsUiState?> = mutableStateOf(null)
    val uiState: State<WorkoutsUiState?> = _uiState
    fun init() {
        launchWithProgress {
            _uiState.value = workoutUiMapper(getWorkoutUseCase())
        }
    }
}