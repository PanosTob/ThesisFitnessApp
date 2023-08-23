package gr.dipae.thesisfitnessapp.data.sport.session.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.session.entity.SportParameterArgument
import javax.inject.Inject

class SportSessionMapper @Inject constructor(
    private val sportsMapper: SportsMapper
) : Mapper {

    operator fun invoke(sportId: String, breakTime: Long, goalParameter: SportParameter?): SportSessionRequest {
        return SportSessionRequest(
            activityId = sportId,
            breakTime = breakTime,
            goalParameter = goalParameter?.let { SportParameterRequest(goalParameter.name, goalParameter.value) }
        )
    }

    operator fun invoke(distance: Long, duration: Long): List<SportParameterRequest> {
        return listOf(
            SportParameterRequest(
                name = "distance",
                value = distance
            ),
            SportParameterRequest(
                name = "duration",
                value = duration
            )
        )
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
            type = sportsMapper.mapSportParameterType(parameter.name),
            value = parameter.value
        )
    }
}