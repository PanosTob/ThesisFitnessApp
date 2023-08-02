package gr.dipae.thesisfitnessapp.data.sport.model

import com.google.firebase.firestore.DocumentId

data class RemoteSport(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val showMap: Boolean = false,
    val imageUrl: String = "",
    val parameters: List<String> = emptyList(),
    val details: RemoteSportDetails = RemoteSportDetails()
)

data class RemoteSportDetails(
    val distanceMeters: Long = 0,
    val durationSeconds: Long = 0
)