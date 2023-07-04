package gr.dipae.thesisfitnessapp.domain.activity.entity

data class Sport(
    val name: String,
    val imageUrl: String,
    val parameters: List<String>,
    val details: SportDetails
)

data class SportDetails(
    val distanceMeters: Long,
    val durationSeconds: Long
)