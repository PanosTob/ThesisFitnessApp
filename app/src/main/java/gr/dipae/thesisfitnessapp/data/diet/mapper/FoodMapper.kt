package gr.dipae.thesisfitnessapp.data.diet.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFoodNutrient
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteSearchFood
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteSearchFoodNutrient
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.diet.entity.FoodNutrientType
import gr.dipae.thesisfitnessapp.domain.diet.entity.SearchFoodNutrientType
import javax.inject.Inject
import kotlin.math.roundToLong

class FoodMapper @Inject constructor() : Mapper {
    operator fun invoke(foods: List<RemoteFood>): List<Food> {
        return foods.map {
            invoke(it)
        }
    }

    fun mapSearchFoods(foods: List<RemoteSearchFood>): List<Food> {
        return foods.map { food ->
            Food(
                id = food.id.toString(),
                name = food.name.toString().trim(),
                proteins = food.nutrients.mapSearchNutrientValue(SearchFoodNutrientType.Protein),
                carbohydrates = food.nutrients.mapSearchNutrientValue(SearchFoodNutrientType.Carbohydrates),
                fats = food.nutrients.mapSearchNutrientValue(SearchFoodNutrientType.Fats),
                calories = food.nutrients.mapSearchNutrientValue(SearchFoodNutrientType.Energy).roundToLong(),
                water = food.nutrients.mapSearchNutrientValue(SearchFoodNutrientType.Water),
                fiber = food.nutrients.mapSearchNutrientValue(SearchFoodNutrientType.Fiber)
            )
        }
    }

    operator fun invoke(food: RemoteFood): Food {
        return Food(
            id = food.id.toString(),
            name = food.name.toString().trim(),
            proteins = food.nutrients.mapNutrientValue(FoodNutrientType.Protein),
            carbohydrates = food.nutrients.mapNutrientValue(FoodNutrientType.Carbohydrates),
            fats = food.nutrients.mapNutrientValue(FoodNutrientType.Fats),
            calories = food.nutrients.mapNutrientValue(FoodNutrientType.Energy).roundToLong(),
            water = food.nutrients.mapNutrientValue(FoodNutrientType.Water),
            fiber = food.nutrients.mapNutrientValue(FoodNutrientType.Fiber)
        )
    }

    private fun List<RemoteFoodNutrient?>?.mapNutrientValue(type: FoodNutrientType): Double {
        return this?.filterNotNull()?.find { it.type == type }?.amount ?: 0.0
    }

    private fun List<RemoteSearchFoodNutrient?>?.mapSearchNutrientValue(type: SearchFoodNutrientType): Double {
        return this?.filterNotNull()?.find { it.type == type }?.value ?: 0.0
    }
}