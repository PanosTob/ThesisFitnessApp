package gr.dipae.thesisfitnessapp.domain.diet.entity

data class Food(
    val name: String,
    val carbohydrates: Long,
    val fats: Long,
    val proteins: Long,
    val foodCategory: FoodCategory
)

enum class FoodCategory {
    HEALTHY, UNHEALTHY
}