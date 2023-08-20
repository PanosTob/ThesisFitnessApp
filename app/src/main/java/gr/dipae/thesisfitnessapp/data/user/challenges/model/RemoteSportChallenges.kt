package gr.dipae.thesisfitnessapp.data.user.challenges.model

import com.google.firebase.firestore.DocumentId

data class RemoteSportChallenges(
    @DocumentId
    val id: String = "",
    val activityId: String = "",
    val activityName: String = "",
    val goal: RemoteChallengeGoal = RemoteChallengeGoal()
)

data class RemoteChallengeGoal(
    val type: String = "",
    val value: Long = 0
)