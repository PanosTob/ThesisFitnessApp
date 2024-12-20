package gr.dipae.thesisfitnessapp.framework.sport

import com.google.firebase.firestore.FirebaseFirestore
import gr.dipae.thesisfitnessapp.data.sport.SportsDataSource
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.util.SPORTS_COLLECTION
import gr.dipae.thesisfitnessapp.util.ext.getDocumentResponse
import gr.dipae.thesisfitnessapp.util.ext.getDocumentsResponse
import javax.inject.Inject

class SportsDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : SportsDataSource {
    override suspend fun getSports(): List<RemoteSport> {
        return fireStore.collection(SPORTS_COLLECTION).getDocumentsResponse()
    }

    override suspend fun getSportById(sportId: String): RemoteSport? {
        return fireStore.document("$SPORTS_COLLECTION/$sportId").getDocumentResponse<RemoteSport>()
    }
}