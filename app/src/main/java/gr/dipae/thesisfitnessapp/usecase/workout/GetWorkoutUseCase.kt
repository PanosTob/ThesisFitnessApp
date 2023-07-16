package gr.dipae.thesisfitnessapp.usecase.workout

import gr.dipae.thesisfitnessapp.domain.workout.WorkoutRepository
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout
import javax.inject.Inject

class GetWorkoutUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(): List<Workout> {
        return try {
            repository.getWorkouts()
        } catch (ex: Exception) {
            emptyList()
        }
    }
}