package gr.dipae.thesisfitnessapp.ui.wizard.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardDailyDietStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardFavoriteActivitiesStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardFitnessLevelStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardNameStep

data class WizardUiState(
    val usernameState: MutableState<String> = mutableStateOf(""),
    val fitnessLevels: List<FitnessLevelUiItem>,
    val sports: List<SportUiItem>,
    val proteinIntakeState: MutableState<String> = mutableStateOf(""),
    val carbohydrateIntakeState: MutableState<String> = mutableStateOf(""),
    val fatsIntakeState: MutableState<String> = mutableStateOf(""),
    val wizardPageState: MutableState<Int> = mutableStateOf(0)
) {
    @Composable
    fun WizardPageContent(pageIndex: Int) {
        return when (pageIndex) {
            0 -> WizardNameStep(usernameState)
            1 -> WizardFitnessLevelStep(fitnessLevels = fitnessLevels)
            2 -> WizardFavoriteActivitiesStep()
            3 -> WizardDailyDietStep()
            else -> Unit
        }
    }
}

data class SportUiItem(
    val name: String,
    val imageUrl: String,
    val parameters: List<String>,
    val selected: MutableState<Boolean> = mutableStateOf(false)
)

data class FitnessLevelUiItem(
    val fitnessLevel: FitnessLevel,
    val isSelectedState: MutableState<Boolean> = mutableStateOf(false)
) {
    val textColor: Color
        get() = if (isSelectedState.value) ColorSecondary else Color.Black
}