package gr.dipae.thesisfitnessapp.usecase.user.history

import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import javax.inject.Inject

class GetTodaySummaryUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): DaySummary? {
        return try {
            repository.getDaySummary()
        } catch (ex: Exception) {
            null
        }
    }
}