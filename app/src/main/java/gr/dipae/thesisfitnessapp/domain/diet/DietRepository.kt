package gr.dipae.thesisfitnessapp.domain.diet

import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary

interface DietRepository {
    suspend fun getFoodList(page: Int): List<Food>
    suspend fun setMacrosDaily(
        calories: String,
        protein: String,
        carbs: String,
        fats: String,
        water: String,
        todaySummary: DaySummary?
    )
}