package gr.dipae.thesisfitnessapp.domain.sport.entity

data class Sport(
    val id: String,
    val name: String,
    val imageUrl: String,
    val parameters: List<SportParameter>,
    val details: SportDetails,
    val favorite: Boolean
)

data class SportDetails(
    val distanceMeters: Long,
    val durationSeconds: Long
)

data class SportParameter(
    val name: String,
    val value: Long = 0
)