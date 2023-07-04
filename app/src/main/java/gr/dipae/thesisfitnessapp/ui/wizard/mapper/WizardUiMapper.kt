package gr.dipae.thesisfitnessapp.ui.wizard.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.ui.wizard.model.SportUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardUiState
import javax.inject.Inject

class WizardUiMapper @Inject constructor() : Mapper {

    operator fun invoke(sports: List<Sport>?): WizardUiState {
        return WizardUiState(
            sports = mapWizardSports(sports)
        )
    }

    private fun mapWizardSports(sports: List<Sport>?): List<SportUiItem> {
        if (sports == null) return emptyList()

        return sports.map {
            SportUiItem(
                name = it.name,
                imageUrl = it.imageUrl,
                parameters = it.parameters
            )
        }
    }
}