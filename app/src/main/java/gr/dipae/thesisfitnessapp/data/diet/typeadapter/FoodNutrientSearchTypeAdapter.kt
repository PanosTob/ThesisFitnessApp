package gr.dipae.thesisfitnessapp.data.diet.typeadapter

import com.squareup.moshi.FromJson
import gr.dipae.thesisfitnessapp.domain.diet.entity.SearchFoodNutrientType

class FoodNutrientSearchTypeAdapter {

    @FromJson
    fun fromJson(number: Any): SearchFoodNutrientType? {
        return when (number.toString().toDoubleOrNull()?.toInt()) {
            1003 -> SearchFoodNutrientType.Protein
            1004 -> SearchFoodNutrientType.Fats
            1005 -> SearchFoodNutrientType.Carbohydrates
            1008 -> SearchFoodNutrientType.Energy
            1051 -> SearchFoodNutrientType.Water
            else -> null
        }
    }
}