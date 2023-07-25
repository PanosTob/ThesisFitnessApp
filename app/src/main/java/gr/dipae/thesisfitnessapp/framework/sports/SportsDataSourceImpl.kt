package gr.dipae.thesisfitnessapp.framework.sports

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.data.sport.SportsDataSource
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.util.ACTIVITIES_DONE_COLLECTION
import gr.dipae.thesisfitnessapp.util.ACTIVITIES_DONE_DETAILS_FIELD
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_DATE
import gr.dipae.thesisfitnessapp.util.SPORTS_COLLECTION
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import gr.dipae.thesisfitnessapp.util.ext.getDocumentResponse
import gr.dipae.thesisfitnessapp.util.ext.getDocumentsResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SportsDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : SportsDataSource {
    private fun getFirebaseUserId() = firebaseAuth.currentUser?.uid ?: throw FirebaseNoSignedInUserException("")
    override suspend fun getSports(): List<RemoteSport> {
        return fireStore.collection(SPORTS_COLLECTION).getDocumentsResponse()
    }

    override suspend fun getSportById(sportId: String): RemoteSport? {
        return fireStore.document("$SPORTS_COLLECTION/$sportId").getDocumentResponse<RemoteSport>()
    }

    override suspend fun initializeSportSession(sportId: String, parameter: SportParameter) {
        val todaySummaryDocument = fireStore
            .collection(USERS_COLLECTION)
            .document(getFirebaseUserId())
            .collection(DAY_SUMMARY_COLLECTION)
            .document()

        todaySummaryDocument.set(
            mapOf(
                DAY_SUMMARY_DATE to FieldValue.serverTimestamp()
            )
        ).await()

        setSportSessionParameter(todaySummaryDocument, parameter)
    }

    private suspend fun setSportSessionParameter(todaySummaryDocument: DocumentReference, parameter: SportParameter) {
        todaySummaryDocument.collection(ACTIVITIES_DONE_COLLECTION).document().set(
            mapOf(
                ACTIVITIES_DONE_DETAILS_FIELD to mapOf(
                    parameter.name to parameter.value
                )
            )
        ).await()
    }
}