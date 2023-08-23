package gr.dipae.thesisfitnessapp.ui.diet.mapper

import androidx.compose.runtime.mutableStateOf
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import gr.dipae.thesisfitnessapp.domain.user.entity.DietGoal
import gr.dipae.thesisfitnessapp.ui.diet.model.DietLobbyUiState
import gr.dipae.thesisfitnessapp.ui.diet.model.NutritionProgressBarUiItem
import gr.dipae.thesisfitnessapp.ui.diet.model.NutritionProgressBarsUiItem
import gr.dipae.thesisfitnessapp.util.ext.formatAmountWith2Decimals
import javax.inject.Inject

class DietLobbyUiMapper @Inject constructor() : Mapper {

    operator fun invoke(diet: DailyDiet?, dietGoal: DietGoal?): DietLobbyUiState {
        return DietLobbyUiState(
            nutritionProgressBars = mutableStateOf(mapNutritionBars(diet, dietGoal))
        )
    }

    private fun mapNutritionBars(diet: DailyDiet?, dietGoal: DietGoal?): NutritionProgressBarsUiItem {
        if (diet == null || dietGoal == null) return NutritionProgressBarsUiItem()
        return NutritionProgressBarsUiItem(
            caloriesBar = mapNutritionBar(diet.calories.toDouble(), dietGoal.calories),
            proteinBar = mapNutritionBar(diet.proteins, dietGoal.protein),
            carbsBar = mapNutritionBar(diet.carbohydrates, dietGoal.carbohydrates),
            fatsBar = mapNutritionBar(diet.fats, dietGoal.fats),
            waterBar = mapNutritionBar(diet.waterML, dietGoal.waterML)
        )
    }

    private fun mapNutritionBar(amount: Double, goalAmount: Long): NutritionProgressBarUiItem {
        return if (goalAmount == 0L) {
            NutritionProgressBarUiItem(
                amount = amount.formatAmountWith2Decimals,
                progressTowardsGoal = 1f
            )
        } else {
            NutritionProgressBarUiItem(
                amount = "${amount.formatAmountWith2Decimals}/${goalAmount}",
                progressTowardsGoal = (amount / goalAmount).toFloat()
            )
        }
    }
}