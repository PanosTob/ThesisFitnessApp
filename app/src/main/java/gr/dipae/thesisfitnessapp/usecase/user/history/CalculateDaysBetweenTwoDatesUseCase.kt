package gr.dipae.thesisfitnessapp.usecase.user.history

import gr.dipae.thesisfitnessapp.domain.history.HistoryRepository
import timber.log.Timber
import javax.inject.Inject

class CalculateDaysBetweenTwoDatesUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    operator fun invoke(startDate: Long, endDate: Long): List<Long> {
        return try {
            repository.calculateDaysBetweenTwoDates(startDate, endDate)
        } catch (ex: Exception) {
            Timber.tag(GetDaySummariesByRangeUseCase::class.java.simpleName).e(ex)
            emptyList()
        }
    }
}