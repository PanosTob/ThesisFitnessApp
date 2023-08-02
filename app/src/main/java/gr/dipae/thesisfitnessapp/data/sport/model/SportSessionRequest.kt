package gr.dipae.thesisfitnessapp.data.sport.model

import com.google.firebase.firestore.PropertyName

data class SportSessionRequest(
    @PropertyName(value = "activityId")
    val sportId: String,
    val breakTime: Long,
)

data class SportParameterRequest(
    val name: String,
    val value: Any,
    val achieved: Boolean
)