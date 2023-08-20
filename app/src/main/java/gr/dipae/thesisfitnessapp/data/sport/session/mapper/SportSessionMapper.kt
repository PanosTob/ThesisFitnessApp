package gr.dipae.thesisfitnessapp.data.sport.session.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportParameterRequest
import gr.dipae.thesisfitnessapp.data.sport.session.model.SportSessionRequest
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import gr.dipae.thesisfitnessapp.domain.sport.session.entity.SportParameterArgument
import gr.dipae.thesisfitnessapp.util.ext.toDoubleWithSpecificDecimals
import javax.inject.Inject

class SportSessionMapper @Inject constructor(
    private val sportsMapper: SportsMapper
) : Mapper {

    operator fun invoke(sportId: String, breakTime: Long): SportSessionRequest {
        return SportSessionRequest(
            sportId = sportId,
            breakTime = breakTime
        )
    }

    operator fun invoke(distance: Double, duration: Long, goalParameter: Pair<SportParameter?, Boolean>): List<SportParameterRequest> {
        return listOf(
            SportParameterRequest(
                name = "distance",
                value = distance.toDoubleWithSpecificDecimals(2),
                achieved = goalParameter.first?.type is SportParameterType.Distance && goalParameter.second
            ),
            SportParameterRequest(
                name = "duration",
                value = duration,
                achieved = goalParameter.first?.type is SportParameterType.Duration && goalParameter.second
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