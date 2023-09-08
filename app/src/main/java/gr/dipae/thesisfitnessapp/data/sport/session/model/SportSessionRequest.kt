package gr.dipae.thesisfitnessapp.data.sport.session.model

import com.google.firebase.firestore.FieldValue

data class SportSessionRequest(
    val activityId: String,
    val breakTime: Long,
    val goalParameter: SportParameterRequest?,
    val date: FieldValue
)

data class SportParameterRequest(
    val name: String,
    val value: Any
)