package gr.dipae.thesisfitnessapp.data.sport

import com.squareup.moshi.Moshi
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.domain.sport.SportsRepository
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.util.ext.fromJson
import gr.dipae.thesisfitnessapp.util.ext.toJson
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


    override suspend fun setSportSession(sportSessionRequest: SportSessionRequest, parameters: List<SportParameterRequest>) {
        return dataSource.setSportSession(sportSessionRequest, parameters)
    }

    override fun sportParameterAsArgumentString(parameter: SportParameter): String {
        return parameter.toJson(moshi)
    }

    override fun getSportParameterArgument(parameterJson: String): SportParameter? {
        return parameterJson.fromJson(moshi, SportParameter::class.java)
    }

    override fun getSportSessionDurationLive(): StateFlow<Long> {
        return dataSource.getSportSessionDurationLive()
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