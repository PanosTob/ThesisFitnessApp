package gr.dipae.thesisfitnessapp.data.sport

import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter

interface SportsDataSource {
    suspend fun getSports(): List<RemoteSport>
    suspend fun getSportById(sportId: String): RemoteSport?
    suspend fun initializeSportSession(sportId: String, parameter: SportParameter)
}