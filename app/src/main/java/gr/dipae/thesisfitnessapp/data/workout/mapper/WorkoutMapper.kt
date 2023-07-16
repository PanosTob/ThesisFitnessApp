package gr.dipae.thesisfitnessapp.data.workout.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.workout.model.RemoteWorkout
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout
import javax.inject.Inject

class WorkoutMapper @Inject constructor() : Mapper {

    operator fun invoke(workouts: List<RemoteWorkout>): List<Workout> {
        return workouts.map { it.toWorkout() }
    }
}