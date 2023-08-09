package gr.dipae.thesisfitnessapp.data.diet

import gr.dipae.thesisfitnessapp.data.diet.mapper.FoodMapper
import gr.dipae.thesisfitnessapp.data.diet.mapper.MacrosMapper
import gr.dipae.thesisfitnessapp.domain.diet.DietRepository
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import javax.inject.Inject

class DietRepositoryImpl @Inject constructor(
    private val dataSource: DietDataSource,
    private val foodMapper: FoodMapper,
    private val macrosMapper: MacrosMapper
) : DietRepository {
    override suspend fun getFoodList(page: Int): List<Food> {
        return foodMapper(dataSource.getFoodList(page))
    }

    override suspend fun setMacrosDaily(calories: String, protein: String, carbs: String, fats: String, water: String, todaySummary: DaySummary?) {
        dataSource.setMacrosDaily(macrosMapper(calories, protein, carbs, fats, water, todaySummary), todaySummary?.id)
    }
}