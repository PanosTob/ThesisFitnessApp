package gr.dipae.thesisfitnessapp.data.history

import gr.dipae.thesisfitnessapp.data.history.model.RemoteDaySummary

interface HistoryDataSource {

    suspend fun getDaySummary(userId: String): RemoteDaySummary?
}