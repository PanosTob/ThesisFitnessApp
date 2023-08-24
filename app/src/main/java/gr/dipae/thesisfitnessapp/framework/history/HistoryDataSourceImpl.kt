package gr.dipae.thesisfitnessapp.framework.history

import gr.dipae.thesisfitnessapp.data.history.HistoryDataSource
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor() : HistoryDataSource {

    override fun calculateDaysBetweenTwoDates(startDate: Long, endDate: Long): List<Long> {
        val startDate = Calendar.getInstance().apply {
            timeInMillis = startDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val endDate = Calendar.getInstance().apply {
            timeInMillis = endDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)

        }

        val timeDiff = endDate.timeInMillis - startDate.timeInMillis
        val daysBetween = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS)

        val oneDayInMillis = 86400000

        val dateOfEachDay = mutableListOf<Long>()
        repeat(daysBetween.toInt()) { i ->
            dateOfEachDay.add(startDate.timeInMillis + (oneDayInMillis * i))
        }
        dateOfEachDay.add(endDate.timeInMillis)

        return dateOfEachDay
    }
}