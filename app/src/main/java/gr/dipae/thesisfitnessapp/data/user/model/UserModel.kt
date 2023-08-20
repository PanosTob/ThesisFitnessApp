package gr.dipae.thesisfitnessapp.data.user.model

import com.google.firebase.firestore.DocumentId

data class RemoteUser(
    @DocumentId
    val id: String = "",
    val name: String = "unknown name",
    val email: String = "unknown email",
    val imgUrl: String = "",
    val bodyWeight: Double = 0.0,
    val muscleMassPercent: Double = 0.0,
    val bodyFatPercent: Double = 0.0,
    val fitnessLevel: String = "",
    val favoriteActivitiesIds: List<String> = emptyList(),
    val dailyStepsGoal: Long = 0,
    val dailyCaloricBurnGoal: Long = 0,
    val dietGoal: RemoteDietGoal = RemoteDietGoal(),
    val challenges: List<RemoteUserSportChallenge> = emptyList()
)

data class RemoteDietGoal(
    val calories: Long = 0,
    val carbohydrates: Double? = 0.0,
    val fatGrams: Double? = 0.0,
    val proteinGrams: Double? = 0.0,
    val waterML: Double? = 0.0
)

data class RemoteUserUpdateRequest(
    val name: String,
    val email: String,
    val imgUrl: String,
    val fitnessLevel: String?,
    val bodyWeight: Double?,
    val muscleMassPercent: Double?,
    val bodyFatPercent: Double?,
    val favoriteActivitiesIds: List<String>,
    val dietGoal: Map<String, Long?>,
    val dailyStepsGoal: Long,
    val dailyCaloricBurnGoal: Long,
    val challenges: List<RemoteUserSportChallenge>
)

data class RemoteUserSportChallenge(
    val challengeId: String = "",
    val activityId: String = "",
    val activityImgUrl: String = "",
    val goal: RemoteUserSportChallengeDetails = RemoteUserSportChallengeDetails(),
    val activityName: String = "",
    val done: Boolean = false,
    val progress: Double = 0.0
)

data class RemoteUserSportChallengeDetails(
    val type: String = "",
    val value: Double = 0.0
)