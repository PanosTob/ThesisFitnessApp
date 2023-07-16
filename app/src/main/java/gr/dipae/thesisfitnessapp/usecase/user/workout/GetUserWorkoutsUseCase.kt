package gr.dipae.thesisfitnessapp.usecase.user.workout

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.workout.entity.Workout
import javax.inject.Inject

class GetUserWorkoutsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<Workout> {
        return try {
            repository.getUserWorkouts()
        } catch (ex: Exception) {
            emptyList()
        }
    }
}