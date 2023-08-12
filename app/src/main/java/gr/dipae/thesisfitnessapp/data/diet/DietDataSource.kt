package gr.dipae.thesisfitnessapp.data.diet

import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood

interface DietDataSource {
    suspend fun getFoodList(page: Int): List<RemoteFood>
    suspend fun setMacrosDaily(dailyDietRequest: DailyDietRequest, todaySummaryId: String?)
    suspend fun searchFoodByName(foodNameQuery: String): List<RemoteFood>
}