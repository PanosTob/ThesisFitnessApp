package gr.dipae.thesisfitnessapp.data.sport

import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport

interface SportsDataSource {
    suspend fun getSports(): List<RemoteSport>
    suspend fun getSportById(sportId: String): RemoteSport?
}