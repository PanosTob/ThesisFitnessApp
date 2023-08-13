package gr.dipae.thesisfitnessapp.usecase.user.history

import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import javax.inject.Inject

class GetTodaySummaryUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): DaySummary? {
        return try {
            val summary = repository.getDaySummary()

            if (summary != null && summary.id.isNotBlank()) {
                val sportsDone = repository.getDaySummarySportsDone(summary.id)
                val workoutsDone = repository.getDaySummaryWorkoutsDone(summary.id)

                summary.sportsDone = sportsDone
                summary.workoutsDone = workoutsDone
            }

            return summary
        } catch (ex: Exception) {
            null
        }
    }
}