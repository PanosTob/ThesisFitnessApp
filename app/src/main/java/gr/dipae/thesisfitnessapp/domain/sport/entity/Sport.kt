package gr.dipae.thesisfitnessapp.domain.sport.entity

data class Sport(
    val id: String,
    val name: String,
    val imageUrl: String,
    val parameters: List<String>,
    val details: SportDetails
)

data class SportDetails(
    val distanceMeters: Long,
    val durationSeconds: Long
)