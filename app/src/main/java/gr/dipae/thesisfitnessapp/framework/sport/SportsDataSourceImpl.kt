package gr.dipae.thesisfitnessapp.framework.sport

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.data.sport.SportsDataSource
import gr.dipae.thesisfitnessapp.data.sport.broadcast.SportSessionBreakTimerBroadcast
import gr.dipae.thesisfitnessapp.data.sport.broadcast.SportSessionBroadcast
import gr.dipae.thesisfitnessapp.data.sport.broadcast.StopWatchBroadcast
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.framework.sport.location.SportLocationProvider
import gr.dipae.thesisfitnessapp.util.ACTIVITIES_DONE_COLLECTION
import gr.dipae.thesisfitnessapp.util.ACTIVITY_STATISTICS
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_DATE
import gr.dipae.thesisfitnessapp.util.SPORTS_COLLECTION
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import gr.dipae.thesisfitnessapp.util.ext.getDocumentResponse
import gr.dipae.thesisfitnessapp.util.ext.getDocumentsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SportsDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val stopWatchBroadcast: StopWatchBroadcast,
    private val sportSessionBroadcast: SportSessionBroadcast,
    private val sportLocationProvider: SportLocationProvider,
    private val breakTimerBroadcast: SportSessionBreakTimerBroadcast
) : SportsDataSource {
    private fun getFirebaseUserId() = firebaseAuth.currentUser?.uid ?: throw FirebaseNoSignedInUserException("")
    override suspend fun getSports(): List<RemoteSport> {
        return fireStore.collection(SPORTS_COLLECTION).getDocumentsResponse()
    }

    override suspend fun getSportById(sportId: String): RemoteSport? {
        return fireStore.document("$SPORTS_COLLECTION/$sportId").getDocumentResponse<RemoteSport>()
    }

    override suspend fun setSportSession(sportDoneRequest: SportSessionRequest, parameters: List<SportParameterRequest>) {
        val todaySummaryDocument = initializeTodaySummary()
        val activityDoneDocument = initializeActivityDone(todaySummaryDocument, sportDoneRequest)
        parameters.forEach {
            activityDoneDocument.collection(ACTIVITY_STATISTICS).document().set(it).await()
        }
    }

    override suspend fun getUserLocation(): Flow<LatLng> {
        return sportLocationProvider.userLiveLocation
    }

    override fun getUserPreviousLocation(): LatLng {
        return sportLocationProvider.userLastLocation
    }

    override fun startUserLocationUpdates(locationUpdateIntervalMillis: Long) {
        return sportLocationProvider.startUserLocationUpdates(locationUpdateIntervalMillis)
    }

    override fun stopUserLocationUpdated() {
        return sportLocationProvider.stopTracking()
    }

    private suspend fun initializeTodaySummary(): DocumentReference {
        val todaySummary = fireStore
            .collection(USERS_COLLECTION)
            .document(getFirebaseUserId())
            .collection(DAY_SUMMARY_COLLECTION)
            .document()

        todaySummary.set(
            mapOf(
                DAY_SUMMARY_DATE to FieldValue.serverTimestamp()
            )
        ).await()

        return todaySummary
    }

    private suspend fun initializeActivityDone(todaySummaryDocument: DocumentReference, sportDoneRequest: SportSessionRequest): DocumentReference {
        val activityDone = todaySummaryDocument.collection(ACTIVITIES_DONE_COLLECTION).document()
        activityDone.set(sportDoneRequest).await()
        return activityDone
    }

    override fun getSportSessionDurationLive(): StateFlow<Long> {
        return stopWatchBroadcast.stopWatchMillisPassed
    }

    override fun getSportSessionDistanceLive(): StateFlow<Double> {
        return sportSessionBroadcast.sportDistance
    }

    override suspend fun setSportSessionDistance(distance: Double) {
        sportSessionBroadcast.refreshSportDistance(distance)
    }

    override fun getSportSessionBreakTimerLive(): StateFlow<Long> {
        return breakTimerBroadcast.breakTimerMillisPassed
    }

    override fun clearSportSessionBreakTimer() {
        breakTimerBroadcast.clear()
    }

    override fun startSportSessionBreakTimer() {
        breakTimerBroadcast.startBreakTimer()
    }

    override fun pauseSportSessionBreakTimer() {
        breakTimerBroadcast.pauseBreakTimer()
    }
}