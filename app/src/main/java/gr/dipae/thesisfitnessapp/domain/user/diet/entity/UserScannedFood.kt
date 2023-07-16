package gr.dipae.thesisfitnessapp.domain.user.diet.entity

data class UserScannedFood(
    val id: String,
    val name: String,
    val calories: Long,
    val proteins: Long,
    val carbohydrates: Long,
    val fats: Long,
    val salt: Long,
    val waterML: Long
)