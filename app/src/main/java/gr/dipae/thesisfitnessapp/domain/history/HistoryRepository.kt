package gr.dipae.thesisfitnessapp.domain.history

import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary

interface HistoryRepository {
    suspend fun getDaySummary(userId: String): DaySummary
}