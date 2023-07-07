package gr.dipae.thesisfitnessapp.framework.sports

import com.google.firebase.firestore.FirebaseFirestore
import gr.dipae.thesisfitnessapp.data.sport.SportsDataSource
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.util.SPORTS_COLLECTION
import gr.dipae.thesisfitnessapp.util.ext.getDocumentsResponse
import javax.inject.Inject

class SportsDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : SportsDataSource {
    override suspend fun getSports(): List<RemoteSport> {
        return fireStore.collection(SPORTS_COLLECTION).getDocumentsResponse()
    }
}