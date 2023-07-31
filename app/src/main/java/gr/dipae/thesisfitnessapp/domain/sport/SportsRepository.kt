package gr.dipae.thesisfitnessapp.domain.sport

import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import kotlinx.coroutines.flow.StateFlow

interface SportsRepository {
    suspend fun getSports(favoriteSportIds: List<String>): List<Sport>
    suspend fun getSportById(sportId: String): Sport?
    suspend fun setSportSession(sportSessionRequest: SportSessionRequest, parameters: List<SportParameterRequest>)
    fun sportParameterAsArgumentString(parameter: SportParameter): String
    fun getSportParameterArgument(parameterJson: String): SportParameter?
    fun getSportSessionDurationLive(): StateFlow<Long>
    fun getSportSessionBreakTimerLive(): StateFlow<Long>
    fun startSportSessionBreakTimer()
    fun pauseSportSessionBreakTimer()
    fun clearSportSessionBreakTimer()
}