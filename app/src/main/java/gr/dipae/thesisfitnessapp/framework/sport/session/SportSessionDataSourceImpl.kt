package gr.dipae.thesisfitnessapp.framework.sport.session

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.data.sport.broadcast.SportSessionBreakTimer
import gr.dipae.thesisfitnessapp.data.sport.broadcast.SportSessionBroadcast
import gr.dipae.thesisfitnessapp.data.sport.broadcast.StopWatchBroadcast
import gr.dipae.thesisfitnessapp.data.sport.session.SportSessionDataSource
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.framework.sport.location.SportSessionLocationProvider
import gr.dipae.thesisfitnessapp.util.ACTIVITIES_DONE_COLLECTION
import gr.dipae.thesisfitnessapp.util.ACTIVITY_STATISTICS
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_COLLECTION
import gr.dipae.thesisfitnessapp.util.DAY_SUMMARY_DATE
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SportSessionDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val sportSessionLocationProvider: SportSessionLocationProvider,
    private val stopWatchBroadcast: StopWatchBroadcast,
    private val breakTimerBroadcast: SportSessionBreakTimer,
    private val sportSessionBroadcast: SportSessionBroadcast
) : SportSessionDataSource {

    private fun getFirebaseUserId() = firebaseAuth.currentUser?.uid ?: throw FirebaseNoSignedInUserException("")

    override suspend fun setSportSession(todaySummaryId: String?, sportDoneRequest: SportSessionRequest, parameters: List<SportParameterRequest>) {
        val todaySummaryDocument = if (todaySummaryId.isNullOrBlank()) {
            initializeTodaySummary()
        } else {
            fireStore
                .collection(USERS_COLLECTION)
                .document(getFirebaseUserId())
                .collection(DAY_SUMMARY_COLLECTION)
                .document(todaySummaryId)
        }

        val activityDoneDocument = initializeActivityDone(todaySummaryDocument, sportDoneRequest)
        parameters.forEach {
            activityDoneDocument.collection(ACTIVITY_STATISTICS).document().set(it).await()
        }
    }

    override fun getUserLocation(): Flow<LatLng?> {
        return sportSessionLocationProvider.userLiveLocation
    }

    override fun getUserPreviousLocation(): LatLng {
        return sportSessionLocationProvider.userPreviousLocation
    }

    override fun getUserMapRoute(): List<List<LatLng>> {
        return sportSessionLocationProvider.userRoute
    }

    override fun setUserPreviousLocation(location: LatLng) {
        sportSessionLocationProvider.setUserPreviousLocation(location)
    }

    override fun startUserLocationUpdates(locationUpdateIntervalMillis: Long) {
        return sportSessionLocationProvider.startUserLocationUpdates(locationUpdateIntervalMillis)
    }

    override fun stopUserLocationUpdated() {
        return sportSessionLocationProvider.stopTracking()
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

    override fun getSportSessionDistanceLive(): StateFlow<Long> {
        return sportSessionBroadcast.sportDistance
    }

    override suspend fun setSportSessionDistance(distance: Long) {
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