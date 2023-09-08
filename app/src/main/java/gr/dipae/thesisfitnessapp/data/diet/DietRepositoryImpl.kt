package gr.dipae.thesisfitnessapp.data.diet

import gr.dipae.thesisfitnessapp.data.diet.mapper.FoodMapper
import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.history.entity.DailyDiet
import javax.inject.Inject

class DietRepositoryImpl @Inject constructor(
    private val dataSource: DietDataSource,
    private val foodMapper: FoodMapper,
) : DietRepository {
    override suspend fun getFoodList(page: Int): List<Food> {
        return foodMapper(dataSource.getFoodList(page))
    }

    override suspend fun searchFoodByName(foodNameQuery: String): List<Food> {
        return foodMapper.mapSearchFoods(dataSource.searchFoodByName(foodNameQuery))
    }

    override suspend fun setDailyDiet(dailyDiet: DailyDiet?) {
        dataSource.setDailyDiet(dailyDiet)
    }
}