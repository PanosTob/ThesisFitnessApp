package gr.dipae.thesisfitnessapp.data.sport.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSport
import gr.dipae.thesisfitnessapp.data.sport.model.RemoteSportDetails
import gr.dipae.thesisfitnessapp.data.sport.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportDetails
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterArgument
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import javax.inject.Inject

class SportsMapper @Inject constructor() : Mapper {

    operator fun invoke(remoteSports: List<RemoteSport>, favoriteSportIds: List<String>): List<Sport> {
        return remoteSports.mapNotNull { mapSport(it, favoriteSportIds) }
    }

    operator fun invoke(sportId: String, breakTime: Long): SportSessionRequest {
        return SportSessionRequest(
            sportId = sportId,
            breakTime = breakTime
        )
    }

    operator fun invoke(distance: Long, duration: Long, goalParameter: SportParameter?): List<SportParameterRequest> {
        return listOf(
            SportParameterRequest(
                name = "distance",
                value = distance,
                achieved = goalParameter?.type is SportParameterType.Distance && distance <= goalParameter.value
            ),
            SportParameterRequest(
                name = "duration",
                value = duration,
                achieved = goalParameter?.type is SportParameterType.Duration && convertMillisToMinutes(duration) >= goalParameter.value
            )
        )
    }

    private fun convertMillisToMinutes(durationMillis: Long): Int {
        return ((durationMillis / (1000 * 60)).mod(60))
    }

    fun mapSportParameterArgument(parameter: SportParameter): SportParameterArgument {
        return SportParameterArgument(
            name = parameter.name,
            value = parameter.value
        )
    }


    fun mapSportParameterFromArgument(parameter: SportParameterArgument?): SportParameter? {
        parameter ?: return null

        return SportParameter(
            name = parameter.name,
            type = sportParameterTypesMap[parameter.name] ?: SportParameterType.Unknown,
            value = parameter.value
        )
    }

    fun mapSport(remoteSport: RemoteSport?, favoriteSportIds: List<String>): Sport? {
        remoteSport ?: return null
        return Sport(
            id = remoteSport.id,
            name = remoteSport.name,
            imageUrl = remoteSport.imageUrl,
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
            "duration" to SportParameterType.Duration(),
            "distance" to SportParameterType.Distance()
        )
    }
}