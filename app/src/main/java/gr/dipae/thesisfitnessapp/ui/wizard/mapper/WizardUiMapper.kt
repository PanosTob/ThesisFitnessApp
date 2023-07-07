package gr.dipae.thesisfitnessapp.ui.wizard.mapper

import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.ui.wizard.model.DietGoalUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.FitnessLevelUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.SportUiItem
import gr.dipae.thesisfitnessapp.ui.wizard.model.WizardUiState
import javax.inject.Inject

class WizardUiMapper @Inject constructor() : Mapper {

    operator fun invoke(sports: List<Sport>?): WizardUiState {
        return WizardUiState(
            wizardSteps = 4,
            sports = mapWizardSports(sports),
            fitnessLevels = FitnessLevel.values().toList().map { FitnessLevelUiItem(it, mutableStateOf(false)) },
            dailyDietGoal = DietGoalUiItem()
        )
    }

    private fun mapWizardSports(sports: List<Sport>?): List<SportUiItem> {
        if (sports == null) return emptyList()

        return sports.map {
            SportUiItem(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl,
                parameters = it.parameters
            )
        }
    }
}