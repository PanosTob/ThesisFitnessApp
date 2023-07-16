package gr.dipae.thesisfitnessapp.usecase.user.history

import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import javax.inject.Inject

class GetDaySummaryUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): DaySummary? {
        return try {
            repository.getDaySummary()?.apply {
                sportsDone = repository.getDaySummarySportsDone(id)
                workoutsDone = repository.getDaySummaryWorkoutsDone(id)
            }
        } catch (ex: Exception) {
            null
        }
    }
}