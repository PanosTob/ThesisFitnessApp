package gr.dipae.thesisfitnessapp.data.diet.model

import com.google.firebase.firestore.PropertyName

data class DailyDietRequest(
    val calories: Long,
    @PropertyName(value = "protein")
    val protein: Double,
    @PropertyName(value = "carbohydrates")
    val carbohydrates: Double,
    @PropertyName(value = "fats")
    val fats: Double,
    @PropertyName(value = "water")
    val water: Double
)