package gr.dipae.thesisfitnessapp.data.diet

import gr.dipae.thesisfitnessapp.data.diet.mapper.FoodMapper
import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import javax.inject.Inject

class DietRepositoryImpl @Inject constructor(
    private val dataSource: DietDataSource,
    private val foodMapper: FoodMapper,
) : DietRepository {
    override suspend fun getFoodList(page: Int): List<Food> {
        return foodMapper(dataSource.getFoodList(page))
    }

    override suspend fun setMacrosDaily(dailyDietRequest: DailyDietRequest, todaySummaryId: String?) {
        dataSource.setMacrosDaily(dailyDietRequest, todaySummaryId)
    }

    override suspend fun searchFoodByName(foodNameQuery: String): List<Food> {
        return foodMapper(dataSource.searchFoodByName(foodNameQuery))
    }
}