package gr.dipae.thesisfitnessapp.data.sport.session

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import gr.dipae.thesisfitnessapp.data.sport.session.mapper.SportSessionMapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.session.SportSessionRepository
import gr.dipae.thesisfitnessapp.domain.sport.session.entity.SportParameterArgument
import gr.dipae.thesisfitnessapp.util.ext.fromJson
import gr.dipae.thesisfitnessapp.util.ext.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SportSessionRepositoryImpl @Inject constructor(
    private val dataSource: SportSessionDataSource,
    private val moshi: Moshi,
    private val sportSessionMapper: SportSessionMapper
) : SportSessionRepository {

    override suspend fun setSportSession(sportId: String, distance: Double, duration: Long, breakTime: Long, goalParameter: Pair<SportParameter?, Boolean>) {
        return dataSource.setSportSession(sportSessionMapper(sportId, breakTime), sportSessionMapper(distance, duration, goalParameter))
    }

    override fun getUserLocation(): Flow<LatLng?> {
        return dataSource.getUserLocation()
    }

    override fun getUserMapRoute(): List<List<LatLng>> {
        return dataSource.getUserMapRoute()
    }

    override fun startUserLocationUpdates(locationUpdateIntervalMillis: Long) {
        return dataSource.startUserLocationUpdates(locationUpdateIntervalMillis)
    }

    override fun getUserPreviousLocation(): LatLng {
        return dataSource.getUserPreviousLocation()
    }

    override fun setUserPreviousLocation(location: LatLng) {
        return dataSource.setUserPreviousLocation(location)
    }

    override fun stopUserLocationUpdated() {
        return dataSource.stopUserLocationUpdated()
    }

    override fun sportParameterAsArgumentString(parameter: SportParameter): String {
        return sportSessionMapper.mapSportParameterArgument(parameter).toJson(moshi)
    }

    override fun getSportParameterArgument(parameterJson: String): SportParameter? {
        return sportSessionMapper.mapSportParameterFromArgument(parameterJson.fromJson(moshi, SportParameterArgument::class.java))
    }

    override fun getSportSessionDurationLive(): StateFlow<Long> {
        return dataSource.getSportSessionDurationLive()
    }

    override suspend fun setSportSessionDistance(distance: Double) {
        return dataSource.setSportSessionDistance(distance)
    }

    override fun getSportSessionDistanceLive(): StateFlow<Double> {
        return dataSource.getSportSessionDistanceLive()
    }

    override fun getSportSessionBreakTimerLive(): StateFlow<Long> {
        return dataSource.getSportSessionBreakTimerLive()
    }

    override fun startSportSessionBreakTimer() {
        dataSource.startSportSessionBreakTimer()
    }

    override fun pauseSportSessionBreakTimer() {
        dataSource.pauseSportSessionBreakTimer()
    }

    override fun clearSportSessionBreakTimer() {
        dataSource.clearSportSessionBreakTimer()
    }
}