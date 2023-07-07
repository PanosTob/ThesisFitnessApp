package gr.dipae.thesisfitnessapp.ui.wizard.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardDailyDietStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardFavoriteActivitiesStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardFitnessLevelStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardNameStep

data class WizardUiState(
    val wizardSteps: Int,
    val usernameState: MutableState<String> = mutableStateOf(""),
    val fitnessLevels: List<FitnessLevelUiItem>,
    val sports: List<SportUiItem>,
    val dailyDietGoal: DietGoalUiItem,
    val wizardPageIndexState: MutableState<Int> = mutableStateOf(0),
    val finishButtonEnabled: MutableState<Boolean> = mutableStateOf(false),
    val goToDashboardState: MutableState<Boolean> = mutableStateOf(false)
) {
    @Composable
    fun WizardPageContent(pageIndex: Int) {
        return when (pageIndex) {
            0 -> WizardNameStep(usernameState)
            1 -> WizardFitnessLevelStep(fitnessLevels = fitnessLevels) { onSelectFitnessLevel(it) }
            2 -> WizardFavoriteActivitiesStep(sports)
            3 -> WizardDailyDietStep(dailyDietGoal)
            else -> Unit
        }
    }

    private fun onSelectFitnessLevel(fitnessLevelUiItem: FitnessLevelUiItem) {
        fitnessLevels.onEach { it.isSelectedState.value = false }
        fitnessLevels.find { it == fitnessLevelUiItem }?.isSelectedState?.value = true
    }

    fun isFinishButtonEnabled(): Boolean =
        if (wizardPageIndexState.value == wizardSteps - 1) {
            (usernameState.value.isNotBlank() && fitnessLevels.any { it.isSelectedState.value }) &&
                    (sports.any { it.selected.value }) &&
                    (dailyDietGoal.caloriesState.value.isNotBlank()) &&
                    (dailyDietGoal.carbohydratesState.value.isNotBlank()) &&
                    (dailyDietGoal.fatsState.value.isNotBlank()) &&
                    (dailyDietGoal.proteinsState.value.isNotBlank()) &&
                    (dailyDietGoal.waterMLState.value.isNotBlank())
        } else {
            true
        }

    fun getFinishButtonText(): Int = if (wizardPageIndexState.value == wizardSteps - 1) R.string.wizard_finish_btn else R.string.wizard_next_btn
}

data class SportUiItem(
    val id: String,
    val name: String,
    val imageUrl: String,
    val parameters: List<String>,
    val selected: MutableState<Boolean> = mutableStateOf(false)
) {
    val color: Color
        get() = if (selected.value) ColorSecondary else Color.Black
}

data class DietGoalUiItem(
    val caloriesState: MutableState<String> = mutableStateOf(""),
    val carbohydratesState: MutableState<String> = mutableStateOf(""),
    val fatsState: MutableState<String> = mutableStateOf(""),
    val proteinsState: MutableState<String> = mutableStateOf(""),
    val waterMLState: MutableState<String> = mutableStateOf(""),
)

data class FitnessLevelUiItem(
    val fitnessLevel: FitnessLevel,
    val isSelectedState: MutableState<Boolean> = mutableStateOf(false)
) {
    val textColor: Color
        get() = if (isSelectedState.value) ColorSecondary else Color.Black
}