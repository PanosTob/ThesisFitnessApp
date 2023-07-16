package gr.dipae.thesisfitnessapp.data.sport.model

import com.google.firebase.firestore.DocumentId
import gr.dipae.thesisfitnessapp.domain.sport.entity.Sport
import gr.dipae.thesisfitnessapp.domain.sport.entity.SportDetails

data class RemoteSport(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val parameters: List<String> = emptyList(),
    val details: RemoteSportDetails = RemoteSportDetails()
) {
    fun toSport(): Sport =
        Sport(
            id = id,
            name = name,
            imageUrl = imageUrl,
            parameters = parameters,
            details = details.toSportDetails()
        )
}

data class RemoteSportDetails(
    val distanceMeters: Long = 0,
    val durationSeconds: Long = 0
) {
    fun toSportDetails(): SportDetails =
        SportDetails(
            distanceMeters = distanceMeters,
            durationSeconds = distanceMeters
        )
}