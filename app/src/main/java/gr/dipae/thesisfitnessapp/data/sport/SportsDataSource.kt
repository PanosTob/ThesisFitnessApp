package gr.dipae.thesisfitnessapp.data.sport

import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SportsDataSource {
    suspend fun getSports(): List<RemoteSport>
    suspend fun getSportById(sportId: String): RemoteSport?
    suspend fun setSportSession(sportDoneRequest: SportSessionRequest, parameters: List<SportParameterRequest>)
    fun getUserLocation(): Flow<LatLng>
    fun getUserPreviousLocation(): LatLng
    fun setUserPreviousLocation(location: LatLng)
    suspend fun setSportSessionDistance(distance: Double)
    fun startUserLocationUpdates(locationUpdateIntervalMillis: Long)
    fun stopUserLocationUpdated()
    fun getSportSessionDurationLive(): StateFlow<Long>
    fun getSportSessionDistanceLive(): StateFlow<Double>
    fun getSportSessionBreakTimerLive(): StateFlow<Long>
    fun startSportSessionBreakTimer()
    fun pauseSportSessionBreakTimer()
    fun clearSportSessionBreakTimer()
}