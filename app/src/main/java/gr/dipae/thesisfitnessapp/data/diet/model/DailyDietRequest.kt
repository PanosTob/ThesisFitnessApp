package gr.dipae.thesisfitnessapp.data.diet.model

import com.google.firebase.firestore.PropertyName

data class DailyDietRequest(
    val calories: Long,
    @PropertyName(value = "proteinGrams")
    val protein: Double,
    @PropertyName(value = "carbonhydrateGrams")
    val carbs: Double,
    @PropertyName(value = "fatGrams")
    val fats: Double,
    @PropertyName(value = "waterML")
    val water: Double
)