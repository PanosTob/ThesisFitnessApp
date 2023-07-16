package gr.dipae.thesisfitnessapp.framework.history

import com.google.firebase.firestore.FirebaseFirestore
import gr.dipae.thesisfitnessapp.data.history.HistoryDataSource
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : HistoryDataSource {
}