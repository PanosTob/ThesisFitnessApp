package gr.dipae.thesisfitnessapp.data.sport.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSportDetails
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportDetails
import javax.inject.Inject

class SportsMapper @Inject constructor() : Mapper {

    operator fun invoke(remoteSports: List<RemoteSport>): List<Sport> {
        return remoteSports.map {
            Sport(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl,
                parameters = it.parameters,
                details = mapSportDetails(it.details)
            )
        }
    }

    private fun mapSportDetails(details: RemoteSportDetails): SportDetails {
        return SportDetails(
            distanceMeters = details.distanceMeters,
            durationSeconds = details.distanceMeters
        )
    }
}