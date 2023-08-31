package gr.dipae.thesisfitnessapp.domain.diet

import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet

interface DietRepository {
    suspend fun getFoodList(page: Int): List<Food>
    suspend fun searchFoodByName(foodNameQuery: String): List<Food>
    suspend fun setDailyDiet(dailyDiet: DailyDiet?)
}