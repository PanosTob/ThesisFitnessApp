package gr.dipae.thesisfitnessapp.ui.sport.mapper

import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.ui.sport.model.SportsUiState
import gr.dipae.thesisfitnessapp.ui.wizard.model.SportUiItem
import javax.inject.Inject

class SportsUiMapper @Inject constructor() : Mapper {

    operator fun invoke(sports: List<Sport>?): SportsUiState {
        return SportsUiState(
            sports = mapSports(sports)
        )
    }

    private fun mapSports(sports: List<Sport>?): List<SportUiItem> {
        sports ?: return emptyList()

        return sports.map {
            SportUiItem(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl,
                parameters = it.parameters,
                selected = mutableStateOf(false)
            )
        }
    }
}