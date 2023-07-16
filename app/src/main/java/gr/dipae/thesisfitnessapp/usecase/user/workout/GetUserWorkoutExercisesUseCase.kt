package gr.dipae.thesisfitnessapp.usecase.user.workout

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkoutExercise
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class GetUserWorkoutExercisesUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {
    suspend operator fun invoke(workoutId: String): List<UserWorkoutExercise> {
        return try {
            repository.getUserWorkoutExercises(workoutId)
        } catch (ex: Exception) {
            emptyList()
        }
    }
}