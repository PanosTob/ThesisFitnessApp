package gr.dipae.thesisfitnessapp.domain.workout

import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout

interface WorkoutRepository {
    suspend fun getWorkouts(): List<Workout>
}