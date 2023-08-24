package gr.dipae.thesisfitnessapp.data.history

import gr.dipae.thesisfitnessapp.domain.history.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dataSource: HistoryDataSource
) : HistoryRepository {

    override fun calculateDaysBetweenTwoDates(startDate: Long, endDate: Long): List<Long> {
        return dataSource.calculateDaysBetweenTwoDates(startDate, endDate)
    }
}