package gr.dipae.thesisfitnessapp.usecase.user.history

import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import timber.log.Timber
import javax.inject.Inject

class GetDaySummariesByRangeUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(startDate: Long?, endDate: Long?): List<DaySummary> {
        return try {
            if (startDate == null || endDate == null) return emptyList()

            repository.getDaySummariesByRange(startDate, endDate)
        } catch (ex: Exception) {
            Timber.tag(GetDaySummariesByRangeUseCase::class.java.simpleName).e(ex)
            emptyList()
        }
    }
}