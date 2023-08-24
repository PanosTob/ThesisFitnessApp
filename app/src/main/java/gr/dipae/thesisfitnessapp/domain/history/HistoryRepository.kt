package gr.dipae.thesisfitnessapp.domain.history

interface HistoryRepository {

    fun calculateDaysBetweenTwoDates(startDate: Long, endDate: Long): List<Long>
}