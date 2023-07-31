package gr.dipae.thesisfitnessapp.data.sport

import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest
import kotlinx.coroutines.flow.StateFlow

interface SportsDataSource {
    suspend fun getSports(): List<RemoteSport>
    suspend fun getSportById(sportId: String): RemoteSport?
    suspend fun setSportSession(sportDoneRequest: SportSessionRequest, parameters: List<SportParameterRequest>)
    fun getSportSessionDurationLive(): StateFlow<Long>
    fun getSportSessionBreakTimerLive(): StateFlow<Long>
    fun startSportSessionBreakTimer()
    fun pauseSportSessionBreakTimer()
    fun clearSportSessionBreakTimer()
}