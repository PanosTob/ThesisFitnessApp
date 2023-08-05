package gr.dipae.thesisfitnessapp.domain.sport.session

import com.google.android.gms.maps.model.LatLng
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SportSessionRepository {
    suspend fun setSportSession(sportId: String, distance: Double, duration: Long, breakTime: Long, goalParameter: Pair<SportParameter?, Boolean>)
    fun getUserLocation(): Flow<LatLng?>
    fun getUserMapRoute(): List<List<LatLng>>
    suspend fun setSportSessionDistance(distance: Double)
    fun startUserLocationUpdates(locationUpdateIntervalMillis: Long)
    fun getUserPreviousLocation(): LatLng
    fun setUserPreviousLocation(location: LatLng)
    fun stopUserLocationUpdated()
    fun sportParameterAsArgumentString(parameter: SportParameter): String
    fun getSportParameterArgument(parameterJson: String): SportParameter?
    fun getSportSessionDurationLive(): StateFlow<Long>
    fun getSportSessionDistanceLive(): StateFlow<Double>
    fun getSportSessionBreakTimerLive(): StateFlow<Long>
    fun startSportSessionBreakTimer()
    fun pauseSportSessionBreakTimer()
    fun clearSportSessionBreakTimer()
}