package gr.dipae.thesisfitnessapp.ui.diet.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DietLobbyUiState(
    val nutritionProgressBars: MutableState<NutritionProgressBarsUiItem> = mutableStateOf(NutritionProgressBarsUiItem())
)

data class NutritionProgressBarsUiItem(
    val caloriesBar: NutritionProgressBarUiItem = NutritionProgressBarUiItem(),
    val proteinBar: NutritionProgressBarUiItem = NutritionProgressBarUiItem(),
    val carbsBar: NutritionProgressBarUiItem = NutritionProgressBarUiItem(),
    val fatsBar: NutritionProgressBarUiItem = NutritionProgressBarUiItem(),
    val waterBar: NutritionProgressBarUiItem = NutritionProgressBarUiItem()
)

data class NutritionProgressBarUiItem(
    val amount: String = "0",
    val progressTowardsGoal: Float = 0f
)