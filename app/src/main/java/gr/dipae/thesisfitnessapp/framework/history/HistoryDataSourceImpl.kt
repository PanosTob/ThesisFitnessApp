package gr.dipae.thesisfitnessapp.framework.history

import com.google.firebase.firestore.FirebaseFirestore
import gr.dipae.thesisfitnessapp.data.history.HistoryDataSource
import gr.dipae.thesisfitnessapp.data.history.model.RemoteDaySummary
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_DATE
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import java.util.Calendar
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : HistoryDataSource {
    override suspend fun getDaySummary(userId: String): RemoteDaySummary? {
        val startOfDayTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val endOfDayTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 24)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }
        return fireStore
            .collection(USERS_COLLECTION)
            .document(userId)
            .collection(DAY_SUMMARY_COLLECTION)
            .whereGreaterThanOrEqualTo(DAY_SUMMARY_DATE, startOfDayTime)
            .whereLessThanOrEqualTo(DAY_SUMMARY_DATE, endOfDayTime)
            .get()
    }
}