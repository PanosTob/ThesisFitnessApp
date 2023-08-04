package gr.dipae.thesisfitnessapp.data.sport

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterArgument
import gr.dipae.thesisfitnessapp.util.ext.fromJson
import gr.dipae.thesisfitnessapp.util.ext.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val dataSource: SportsDataSource,
    private val moshi: Moshi,
    private val sportsMapper: SportsMapper
) : SportsRepository {
    override suspend fun getSports(favoriteSportIds: List<String>): List<Sport> {
        return sportsMapper(dataSource.getSports(), favoriteSportIds)
    }

    override suspend fun getSportById(sportId: String): Sport? {
        return sportsMapper.mapSport(dataSource.getSportById(sportId), emptyList())
    }


    override suspend fun setSportSession(sportId: String, distance: Double, duration: Long, breakTime: Long, goalParameter: Pair<SportParameter?, Boolean>) {
        return dataSource.setSportSession(sportsMapper(sportId, breakTime), sportsMapper(distance, duration, goalParameter))
    }

    override fun getUserLocation(): Flow<LatLng> {
        return dataSource.getUserLocation()
    }

    override fun getUserMapRoute(): List<LatLng> {
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
        return sportsMapper.mapSportParameterArgument(parameter).toJson(moshi)
    }

    override fun getSportParameterArgument(parameterJson: String): SportParameter? {
        return sportsMapper.mapSportParameterFromArgument(parameterJson.fromJson(moshi, SportParameterArgument::class.java))
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