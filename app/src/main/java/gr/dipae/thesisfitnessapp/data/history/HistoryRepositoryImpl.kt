package gr.dipae.thesisfitnessapp.data.history

import gr.dipae.thesisfitnessapp.data.history.mapper.HistoryMapper
import gr.dipae.thesisfitnessapp.domain.history.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dataSource: HistoryDataSource,
    private val historyMapper: HistoryMapper
) : HistoryRepository {
}