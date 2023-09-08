package gr.dipae.thesisfitnessapp.data.diet

import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteSearchFood
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet

interface DietDataSource {
    suspend fun getFoodList(page: Int): List<RemoteFood>
    suspend fun searchFoodByName(foodNameQuery: String): List<RemoteSearchFood>
    suspend fun setDailyDiet(dailyDiet: DailyDiet?)
}