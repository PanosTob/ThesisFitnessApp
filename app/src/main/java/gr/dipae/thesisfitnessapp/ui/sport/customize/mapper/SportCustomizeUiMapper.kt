package gr.dipae.thesisfitnessapp.ui.sport.customize.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.ui.sport.customize.model.SportCustomizeUiState
import gr.dipae.thesisfitnessapp.ui.sport.model.SportParameterUiItem
import javax.inject.Inject

class SportCustomizeUiMapper @Inject constructor() : Mapper {

    operator fun invoke(sport: Sport?): SportCustomizeUiState {
        return SportCustomizeUiState(
            sportId = sport?.id ?: "",
            name = sport?.name ?: "",
            parameters = sport?.parameters?.map { SportParameterUiItem(it) } ?: emptyList(),
        )
    }
}