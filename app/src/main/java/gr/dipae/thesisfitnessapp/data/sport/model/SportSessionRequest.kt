package gr.dipae.thesisfitnessapp.data.sport.model

import com.google.firebase.firestore.PropertyName

data class SportSessionRequest(
    @PropertyName(value = "activityId")
    val sportId: String
)

data class SportParameterRequest(
    val name: String,
    val value: Long,
    val achieved: Boolean
)