package gr.dipae.thesisfitnessapp.usecase.user.workout

import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkout
import javax.inject.Inject

class GetUserWorkoutsUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<UserWorkout> {
        return try {
            repository.getUserWorkouts()
        } catch (ex: Exception) {
            emptyList()
        }
    }
}