package gr.dipae.thesisfitnessapp.data.user.model

import com.google.firebase.firestore.DocumentId

data class RemoteUser(
    @DocumentId
    val id: String = "",
    val name: String = "unknown name",
    val email: String = "unknown email",
    val fitnessLevel: String = "",
    val favoriteActivities: List<String> = emptyList(),
    val dietGoal: RemoteDietGoal = RemoteDietGoal()
)

data class RemoteDietGoal(
    val calories: Long = 0,
    val carbohydrates: Double? = 0.0,
    val fatGrams: Double? = 0.0,
    val proteinGrams: Double? = 0.0,
    val waterML: Double? = 0.0
)