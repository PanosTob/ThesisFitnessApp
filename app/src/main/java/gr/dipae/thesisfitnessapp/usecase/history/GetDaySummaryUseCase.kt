package gr.dipae.thesisfitnessapp.usecase.history

import gr.dipae.thesisfitnessapp.domain.history.HistoryRepository
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import javax.inject.Inject

class GetDaySummaryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(userId: String): DaySummary? {
        return try {
            repository.getDaySummary(userId)
        } catch (ex: Exception) {
            null
        }
    }
}