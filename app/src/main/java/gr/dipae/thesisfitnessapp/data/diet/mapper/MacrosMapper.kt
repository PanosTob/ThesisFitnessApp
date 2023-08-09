package gr.dipae.thesisfitnessapp.data.diet.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import javax.inject.Inject

class MacrosMapper @Inject constructor() : Mapper {

    operator fun invoke(calories: String, protein: String, carbs: String, fats: String, water: String, todaySummary: DaySummary?): DailyDietRequest {
        val dailyCalorieAccumulation = (calories.toLongOrNull() ?: 0).plus(todaySummary?.dailyDiet?.calories ?: 0)
        val dailyProteinAccumulation = (protein.toLongOrNull() ?: 0).plus(todaySummary?.dailyDiet?.proteins ?: 0.0)
        val dailyCarbAccumulation = (carbs.toLongOrNull() ?: 0).plus(todaySummary?.dailyDiet?.carbohydrates ?: 0.0)
        val dailyFatAccumulation = (fats.toLongOrNull() ?: 0).plus(todaySummary?.dailyDiet?.fats ?: 0.0)
        val dailyWaterAccumulation = (water.toLongOrNull() ?: 0).plus(todaySummary?.dailyDiet?.waterML ?: 0.0)

        return DailyDietRequest(
            calories = dailyCalorieAccumulation,
            protein = dailyProteinAccumulation,
            carbs = dailyCarbAccumulation,
            fats = dailyFatAccumulation,
            water = dailyWaterAccumulation
        )
    }
}