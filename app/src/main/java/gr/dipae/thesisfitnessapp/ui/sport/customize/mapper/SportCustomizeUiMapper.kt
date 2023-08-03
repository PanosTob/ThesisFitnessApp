package gr.dipae.thesisfitnessapp.ui.sport.customize.mapper

import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameter
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportParameterType
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportParameterUiItem
import javax.inject.Inject

class SportCustomizeUiMapper @Inject constructor() : Mapper {

    operator fun invoke(sport: Sport?): SportCustomizeUiState {
        val sportParameters = mapSportParameters(sport?.parameters)
        return SportCustomizeUiState(
            sportId = sport?.id ?: "",
            name = sport?.name ?: "",
            hasMap = sport?.hasMap ?: false,
            parameters = sportParameters
        )
    }

    private fun mapSportParameters(parameters: List<SportParameter>?): List<SportParameterUiItem> {
        parameters ?: return emptyList()

        return parameters.mapNotNull {
            val nameFirstLetter = it.name.firstOrNull() ?: return@mapNotNull null
            val unitSuffix = when (it.type) {
                is SportParameterType.Distance -> "/meters"
                is SportParameterType.Duration -> "/minutes"
                SportParameterType.Unknown -> ""
            }
            val placeholder = when (it.type) {
                is SportParameterType.Distance -> R.string.sport_customize_distance_placeholder
                is SportParameterType.Duration -> R.string.sport_customize_duration_placeholder
                SportParameterType.Unknown -> R.string.empty
            }
            SportParameterUiItem(
                name = it.name.replace(nameFirstLetter, nameFirstLetter.uppercaseChar()),
                unitSuffix = unitSuffix,
                placeholder = placeholder,
                iconRes = parameterIconMap[it.name] ?: -1
            )
        }
    }

    fun mapSportParameter(parameter: SportParameterUiItem?): SportParameter? {
        parameter ?: return null

        val parameterValue = parameter.parameterValue.value.toLongOrNull() ?: return null
        return SportParameter(
            name = parameter.name,
            type = SportsMapper.sportParameterTypesMap[parameter.name] ?: SportParameterType.Unknown,
            value = parameterValue
        )
    }

    companion object {
        val parameterIconMap: LinkedHashMap<String, Int> =
            linkedMapOf(
                "duration" to R.drawable.ic_duration,
                "distance" to R.drawable.ic_distance
            )
    }
}