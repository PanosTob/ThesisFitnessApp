package gr.dipae.thesisfitnessapp.data.history.model

import com.google.firebase.firestore.DocumentId

data class RemoteDaySummary(
    @DocumentId
    val id: String = "",
)