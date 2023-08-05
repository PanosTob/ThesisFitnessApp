package gr.dipae.thesisfitnessapp.data.sport.mapper

import gr.dipae.thesisfitnessapp.BuildConfig
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSportDetails
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportDetails
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import javax.inject.Inject

class SportsMapper @Inject constructor() : Mapper {

    operator fun invoke(remoteSports: List<RemoteSport>, favoriteSportIds: List<String>): List<Sport> {
        return remoteSports.mapNotNull { mapSport(it, favoriteSportIds) }
    }

    fun mapSport(remoteSport: RemoteSport?, favoriteSportIds: List<String>): Sport? {
        remoteSport ?: return null
        return Sport(
            id = remoteSport.id,
            name = remoteSport.name,
            hasMap = remoteSport.showMap,
            imageUrl = "${BuildConfig.GOOGLE_STORAGE_FIREBASE}${remoteSport.imageUrl}${BuildConfig.GOOGLE_STORAGE_FIREBASE_QUERY_PARAMS}",
            parameters = mapSportParameters(remoteSport.parameters),
            details = mapSportDetails(remoteSport.details),
            favorite = favoriteSportIds.contains(remoteSport.id)
        )
    }

    private fun mapSportParameters(parameters: List<String>): List<SportParameter> {
        return parameters.map { SportParameter(name = it, type = sportParameterTypesMap[it] ?: SportParameterType.Unknown) }
    }

    private fun mapSportDetails(details: RemoteSportDetails): SportDetails {
        return SportDetails(
            distanceMeters = details.distanceMeters,
            durationSeconds = details.distanceMeters
        )
    }


    companion object {
        val sportParameterTypesMap = linkedMapOf(
            "duration" to SportParameterType.Duration,
            "distance" to SportParameterType.Distance
        )
    }
}