package gr.dipae.thesisfitnessapp.domain.sport

import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import kotlinx.coroutines.flow.StateFlow

interface SportsRepository {
    suspend fun getSports(favoriteSportIds: List<String>): List<Sport>
    suspend fun getSportById(sportId: String): Sport?
    suspend fun setSportSession(sportId: String, distance: Long, duration: Long, breakTime: Long, goalParameter: SportParameter?)
    fun sportParameterAsArgumentString(parameter: SportParameter): String
    fun getSportParameterArgument(parameterJson: String): SportParameter?
    fun getSportSessionDurationLive(): StateFlow<Long>
    fun getSportSessionDistanceLive(): StateFlow<Long>
    fun getSportSessionBreakTimerLive(): StateFlow<Long>
    fun startSportSessionBreakTimer()
    fun pauseSportSessionBreakTimer()
    fun clearSportSessionBreakTimer()
}