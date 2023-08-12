package gr.dipae.thesisfitnessapp.ui.diet.foodselection.mapper

import androidx.compose.runtime.toMutableStateList
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodSelectionUiState
import gr.dipae.thesisfitnessapp.ui.diet.foodselection.model.FoodUiItem
import javax.inject.Inject

class FoodSelectionUiMapper @Inject constructor() : Mapper {

    operator fun invoke(foods: List<Food>): FoodSelectionUiState {
        return FoodSelectionUiState(
            foodList = mapFoods(foods).toMutableStateList()
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

    fun mapFoodNutrientsRequest(foodUiItem: FoodUiItem?): Food? {
        foodUiItem ?: return null
        return Food(
            id = foodUiItem.id,
            name = foodUiItem.name,
            proteins = foodUiItem.proteins.toDoubleOrNull() ?: 0.0,
            carbohydrates = foodUiItem.carbohydrates.toDoubleOrNull() ?: 0.0,
            fats = foodUiItem.fats.toDoubleOrNull() ?: 0.0,
            calories = foodUiItem.calories.toLongOrNull() ?: 0,
            water = foodUiItem.water.toDoubleOrNull() ?: 0.0,
            fiber = foodUiItem.fiber.toDoubleOrNull() ?: 0.0
        )
    }
}