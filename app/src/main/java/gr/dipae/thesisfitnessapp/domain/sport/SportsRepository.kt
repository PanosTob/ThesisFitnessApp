package gr.dipae.thesisfitnessapp.domain.sport

import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter

interface SportsRepository {
    suspend fun getSports(favoriteSportIds: List<String>): List<Sport>
    suspend fun getSportById(sportId: String): Sport?
    suspend fun initializeSportSession(sportId: String, parameter: SportParameter)
}