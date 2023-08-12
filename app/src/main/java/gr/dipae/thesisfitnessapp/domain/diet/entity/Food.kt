package gr.dipae.thesisfitnessapp.domain.diet.entity

import androidx.annotation.Keep

data class Food(
    val id: String,
    val name: String,
    val proteins: Double,
    val carbohydrates: Double,
    val fats: Double,
    val calories: Long,
    val water: Double,
    val fiber: Double
)

@Keep
enum class FoodNutrientType {
    Protein, Carbohydrates, Energy, Water, Fats, Fiber
}