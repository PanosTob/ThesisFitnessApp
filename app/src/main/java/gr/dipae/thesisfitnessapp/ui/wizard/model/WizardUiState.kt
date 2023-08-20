package gr.dipae.thesisfitnessapp.ui.wizard.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import gr.dipae.thesisfitnessapp.R
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import gr.dipae.thesisfitnessapp.ui.theme.ColorSecondary
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardDailyDietStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardFavoriteActivitiesStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardNameStep
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardStepGoal
import gr.dipae.thesisfitnessapp.ui.wizard.composable.WizardWeightStep
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_CALORIES
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_CARBOHYDRATES
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_FATS
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_PROTEIN
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_WATER

data class WizardUiState(
    val wizardSteps: Int,
    val usernameState: MutableState<String> = mutableStateOf(""),
    val fitnessLevels: List<FitnessLevelUiItem>,
    val bodyDetails: BodyDetailsUiItem = BodyDetailsUiItem(),
    val sports: List<WizardSportUiItem>,
    val dailyDietGoal: DietGoalUiItem,
    val wizardTrackingMovementGoal: WizardTrackingMovementGoal = WizardTrackingMovementGoal(),
    val wizardPageIndexState: MutableState<Int> = mutableStateOf(0),
    val finishButtonEnabled: MutableState<Boolean> = mutableStateOf(false),
    val goToDashboardState: MutableState<Boolean> = mutableStateOf(false)
) {
    private val selectedFitnessLevel = fitnessLevels.find { it.isSelectedState.value }?.fitnessLevel?.name ?: FitnessLevel.Unknown.name

    @Composable
    fun WizardPageContent(pageIndex: Int) {
        return when (pageIndex) {
            0 -> WizardNameStep(usernameState)
            1 -> WizardWeightStep(bodyDetails)
            2 -> WizardStepGoal(wizardTrackingMovementGoal)
            3 -> WizardFavoriteActivitiesStep(sports)
            4 -> WizardDailyDietStep(dailyDietGoal)
            else -> Unit
        }
    }

    private fun onSelectFitnessLevel(fitnessLevelUiItem: FitnessLevelUiItem) {
        fitnessLevels.onEach { it.isSelectedState.value = false }
        fitnessLevels.find { it == fitnessLevelUiItem }?.isSelectedState?.value = true
    }

    fun isFinishButtonEnabled(): Boolean =
        if (wizardPageIndexState.value == wizardSteps - 1) {
            usernameState.value.isNotBlank() &&
                    sports.any { it.selected.value } &&
                    bodyDetails.bodyWeightState.value.isNotBlank() &&
                    wizardTrackingMovementGoal.dailyStepGoal.value.isNotBlank() &&
                    wizardTrackingMovementGoal.dailyCaloricBurnGoal.value.isNotBlank()
        } else {
            true
        }

    fun getFinishButtonText(): Int = if (wizardPageIndexState.value == wizardSteps - 1) R.string.wizard_finish_btn else R.string.wizard_next_btn

    fun toUserDetails(): UserWizardDetails {
        return UserWizardDetails(
            name = usernameState.value,
            fitnessLevel = selectedFitnessLevel,
            favoriteActivitiesIds = sports.filter { it.selected.value }.map { it.id },
            bodyWeight = bodyDetails.bodyWeightState.value.toDoubleOrNull(),
            muscleMassPercent = bodyDetails.muscleMassPercentState.value.toDoubleOrNull(),
            bodyFatPercent = bodyDetails.bodyFatPercentState.value.toDoubleOrNull(),
            dietGoal = mapOf(
                USER_DIET_GOAL_CARBOHYDRATES to dailyDietGoal.caloriesState.value.toLongOrNull(),
                USER_DIET_GOAL_FATS to dailyDietGoal.fatsState.value.toLongOrNull(),
                USER_DIET_GOAL_PROTEIN to dailyDietGoal.proteinsState.value.toLongOrNull(),
                USER_DIET_GOAL_CALORIES to dailyDietGoal.caloriesState.value.toLongOrNull(),
                USER_DIET_GOAL_WATER to dailyDietGoal.waterMLState.value.toLongOrNull()
            ),
            dailyStepsGoal = wizardTrackingMovementGoal.dailyStepGoal.value.toLongOrNull() ?: 0L,
            dailyCaloricBurnGoal = wizardTrackingMovementGoal.dailyCaloricBurnGoal.value.toLongOrNull() ?: 0L,
        )
    }
}

data class BodyDetailsUiItem(
    val bodyWeightState: MutableState<String> = mutableStateOf(""),
    val muscleMassPercentState: MutableState<String> = mutableStateOf(""),
    val bodyFatPercentState: MutableState<String> = mutableStateOf(""),
)

data class WizardSportUiItem(
    val id: String,
    val name: String,
    val imageUrl: String,
    val parameters: List<String>,
    val selected: MutableState<Boolean> = mutableStateOf(false)
) {
    val color: Color
        get() = if (selected.value) ColorSecondary else Color.White
}

data class WizardTrackingMovementGoal(
    val dailyStepGoal: MutableState<String> = mutableStateOf(""),
    val dailyCaloricBurnGoal: MutableState<String> = mutableStateOf(""),
)

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
        get() = if (isSelectedState.value) ColorSecondary else Color.White
}