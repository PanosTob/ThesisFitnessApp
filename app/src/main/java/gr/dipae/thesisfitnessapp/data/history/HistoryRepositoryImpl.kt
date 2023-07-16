package gr.dipae.thesisfitnessapp.data.history

import gr.dipae.thesisfitnessapp.data.history.mapper.HistoryMapper
import gr.dipae.thesisfitnessapp.domain.history.HistoryRepository
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dataSource: HistoryDataSource,
    private val historyMapper: HistoryMapper
) : HistoryRepository {
    override suspend fun getDaySummary(userId: String): DaySummary? {
        return historyMapper(dataSource.getDaySummary(userId))
    }
}