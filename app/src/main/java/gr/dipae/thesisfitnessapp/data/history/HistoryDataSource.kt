package gr.dipae.thesisfitnessapp.data.history

interface HistoryDataSource {
    fun calculateDaysBetweenTwoDates(startDate: Long, endDate: Long): List<Long>
}