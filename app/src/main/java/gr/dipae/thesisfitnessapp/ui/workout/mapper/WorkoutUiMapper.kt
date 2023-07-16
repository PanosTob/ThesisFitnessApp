package gr.dipae.thesisfitnessapp.ui.workout.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout
import gr.dipae.thesisfitnessapp.ui.workout.model.WorkoutsUiState
import gr.dipae.thesisfitnessapp.ui.workout.model.toWorkoutUiItem
import javax.inject.Inject

class WorkoutUiMapper @Inject constructor() : Mapper {

    operator fun invoke(workouts: List<Workout>): WorkoutsUiState {
        return WorkoutsUiState(
            workoutList = workouts.map { it.toWorkoutUiItem() }
        )
    }
}