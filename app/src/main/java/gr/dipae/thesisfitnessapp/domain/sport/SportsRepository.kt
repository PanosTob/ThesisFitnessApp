package gr.dipae.thesisfitnessapp.domain.sport

import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport

interface SportsRepository {
    suspend fun getSports(): List<Sport>
    suspend fun getSportById(sportId: String): Sport?
}