package gr.dipae.thesisfitnessapp.data.diet.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFood
import gr.dipae.thesisfitnessapp.data.diet.model.RemoteFoodNutrient
import gr.dipae.thesisfitnessapp.domain.diet.entity.Food
import gr.dipae.thesisfitnessapp.domain.diet.entity.FoodNutrientType
import javax.inject.Inject
import kotlin.math.roundToLong

class FoodMapper @Inject constructor() : Mapper {
    operator fun invoke(foods: List<RemoteFood>): List<Food> {
        return foods.map {
            Food(
                id = it.id.toString(),
                name = it.name.toString().trim(),
                proteins = it.nutrients.mapNutrientValue(FoodNutrientType.Protein),
                carbohydrates = it.nutrients.mapNutrientValue(FoodNutrientType.Carbohydrates),
                fats = it.nutrients.mapNutrientValue(FoodNutrientType.Fats),
                calories = it.nutrients.mapNutrientValue(FoodNutrientType.Energy).roundToLong(),
                water = it.nutrients.mapNutrientValue(FoodNutrientType.Water),
                fiber = it.nutrients.mapNutrientValue(FoodNutrientType.Fiber)
            )
        }
    }

    private fun List<RemoteFoodNutrient?>?.mapNutrientValue(type: FoodNutrientType): Double {
        return this?.filterNotNull()?.find { it.type == type }?.amount ?: 0.0
    }
}