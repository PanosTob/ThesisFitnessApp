package gr.dipae.thesisfitnessapp.data.diet.typeadapter

import com.squareup.moshi.FromJson
import gr.dipae.thesisfitnessapp.domain.diet.entity.FoodNutrientType

class FoodNutrientTypeAdapter {

    @FromJson
    fun fromJson(number: Any): FoodNutrientType? {
        return when (number.toString().toIntOrNull()) {
            203 -> FoodNutrientType.Protein
            204 -> FoodNutrientType.Fats
            205 -> FoodNutrientType.Carbohydrates
            208 -> FoodNutrientType.Energy
            255 -> FoodNutrientType.Water
            291 -> FoodNutrientType.Fiber
            else -> null
        }
    }
}