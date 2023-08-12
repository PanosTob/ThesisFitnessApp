package gr.dipae.thesisfitnessapp.domain.diet

import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food

interface DietRepository {
    suspend fun getFoodList(page: Int): List<Food>
    suspend fun setMacrosDaily(dailyDietRequest: DailyDietRequest, todaySummaryId: String?)
    suspend fun searchFoodByName(foodNameQuery: String): List<Food>
}