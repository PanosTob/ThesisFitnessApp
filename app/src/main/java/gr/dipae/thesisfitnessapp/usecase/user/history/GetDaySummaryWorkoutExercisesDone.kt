package gr.dipae.thesisfitnessapp.usecase.user.history

import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import javax.inject.Inject

class GetDaySummaryWorkoutExercisesDone @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(daySummaryId: String, workoutId: String): List<WorkoutExerciseDone> {
        return try {
            repository.getDaySummaryWorkoutExercisesDone(daySummaryId, workoutId)
        } catch (ex: Exception) {
            emptyList()
        }
    }
}