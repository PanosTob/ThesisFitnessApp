package gr.dipae.thesisfitnessapp.ui.sport.customize.mapper

import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportParameterUiItem
import javax.inject.Inject

class SportCustomizeUiMapper @Inject constructor() : Mapper {

    operator fun invoke(sport: Sport?): SportCustomizeUiState {
        val sportParameters = mapSportParameters(sport?.parameters)
        return SportCustomizeUiState(
            sportId = sport?.id ?: "",
            name = sport?.name ?: "",
            parameters = sportParameters,
            selectedParameter = mutableStateOf(sportParameters.firstOrNull())
        )
    }

    private fun mapSportParameters(parameters: List<String>?): List<SportParameterUiItem> {
        parameters ?: return emptyList()

        return parameters.map { SportParameterUiItem(it, iconRes = parameterIconMap[it] ?: -1) }
    }

    companion object {
        val parameterIconMap: LinkedHashMap<String, Int> =
            linkedMapOf(
                "duration" to R.drawable.ic_duration,
                "distance" to R.drawable.ic_distance
            )
    }
}