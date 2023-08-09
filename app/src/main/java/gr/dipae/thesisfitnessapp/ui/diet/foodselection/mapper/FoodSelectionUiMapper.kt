package gr.dipae.thesisfitnessapp.ui.diet.foodselection.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodSelectionUiState
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodUiItem
import javax.inject.Inject

class FoodSelectionUiMapper @Inject constructor() : Mapper {

    operator fun invoke(foods: List<Food>): FoodSelectionUiState {
        return FoodSelectionUiState(
            foodList = mapFoods(foods)
        )
    }

    fun mapFoods(foods: List<Food>): MutableList<FoodUiItem> {
        return foods.map {
            FoodUiItem(
                id = it.id,
                name = it.name,
                proteins = it.proteins.toString(),
                carbohydrates = it.carbohydrates.toString(),
                fats = it.fats.toString(),
                calories = it.calories.toString(),
                water = it.water.toString(),
                fiber = it.fiber.toString()
            )
        }.toMutableList()
    }
}