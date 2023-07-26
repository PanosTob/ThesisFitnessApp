package gr.dipae.thesisfitnessapp.data.sport

import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest

interface SportsDataSource {
    suspend fun getSports(): List<RemoteSport>
    suspend fun getSportById(sportId: String): RemoteSport?
    suspend fun setSportSession(sportDoneRequest: SportSessionRequest, parameters: List<SportParameterRequest>)
}