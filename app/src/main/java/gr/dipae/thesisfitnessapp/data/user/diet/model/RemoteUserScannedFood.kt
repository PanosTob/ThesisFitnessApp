package gr.dipae.thesisfitnessapp.data.user.diet.model

import com.google.firebase.firestore.DocumentId
import gr.dipae.thesisfitnessapp.domain.user.diet.entity.UserScannedFood

data class RemoteUserScannedFood(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val calories: Long = 0,
    val proteins: Long = 0,
    val carbohydrates: Long = 0,
    val fats: Long = 0,
    val waterML: Long = 0,
    val salt: Long = 0
) {
    fun toUserScanneFood() =
        UserScannedFood(
            id = id,
            name = name,
            calories = calories,
            proteins = proteins,
            carbohydrates = carbohydrates,
            fats = fats,
            waterML = waterML,
            salt = salt
        )
}