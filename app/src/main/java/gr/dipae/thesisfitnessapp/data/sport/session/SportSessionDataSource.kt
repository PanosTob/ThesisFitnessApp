package gr.dipae.thesisfitnessapp.data.sport.session

import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportSessionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SportSessionDataSource {
    suspend fun setSportSession(todaySummaryId: String?, sportDoneRequest: SportSessionRequest, parameters: List<SportParameterRequest>)
    fun getUserLocation(): Flow<LatLng?>
    fun getUserPreviousLocation(): LatLng
    fun getUserMapRoute(): List<List<LatLng>>
    fun setUserPreviousLocation(location: LatLng)
    suspend fun setSportSessionDistance(distance: Long)
    fun startUserLocationUpdates(locationUpdateIntervalMillis: Long)
    fun stopUserLocationUpdated()
    fun getSportSessionDurationLive(): StateFlow<Long>
    fun getSportSessionDistanceLive(): StateFlow<Long>
    fun getSportSessionBreakTimerLive(): StateFlow<Long>
    fun startSportSessionBreakTimer()
    fun pauseSportSessionBreakTimer()
    fun clearSportSessionBreakTimer()
}