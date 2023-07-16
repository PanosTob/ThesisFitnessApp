package gr.dipae.thesisfitnessapp.data.workout

import gr.dipae.thesisfitnessapp.data.workout.model.RemoteWorkout

interface WorkoutDataSource {
    suspend fun getWorkouts(): List<RemoteWorkout>
}