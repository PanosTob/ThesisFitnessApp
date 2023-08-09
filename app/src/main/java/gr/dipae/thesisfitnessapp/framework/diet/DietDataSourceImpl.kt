package gr.dipae.thesisfitnessapp.framework.diet

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.data.diet.DietDataSource
import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_DAILY_DIET
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_DATE
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import gr.dipae.thesisfitnessapp.util.ext.requireNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DietDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val api: FoodApi
) : DietDataSource {
    private fun getFirebaseUserId() = firebaseAuth.currentUser?.uid ?: throw FirebaseNoSignedInUserException("")

    override suspend fun getFoodList(page: Int): List<RemoteFood> {
        return withContext(Dispatchers.IO) {
            api.getFoodList(page = page).requireNotNull()
        }
    }

    override suspend fun setMacrosDaily(dailyDietRequest: DailyDietRequest, todaySummaryId: String?) {
        val daySummaryCollection = fireStore
            .collection(USERS_COLLECTION)
            .document(getFirebaseUserId())
            .collection(DAY_SUMMARY_COLLECTION)

        val daySummaryDoc = if (todaySummaryId != null) daySummaryCollection.document(todaySummaryId) else daySummaryCollection.document()

        daySummaryDoc
            .set(
                mapOf(
                    DAY_SUMMARY_DATE to FieldValue.serverTimestamp(),
                    DAY_SUMMARY_DAILY_DIET to dailyDietRequest
                )
            )
            .await()
    }
}