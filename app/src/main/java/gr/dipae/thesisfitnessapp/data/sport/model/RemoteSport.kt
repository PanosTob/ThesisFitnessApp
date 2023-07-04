package gr.dipae.thesisfitnessapp.data.sport.model

data class RemoteSport(
    val name: String = "",
    val imageUrl: String = "",
    val parameters: List<String> = emptyList(),
    val details: RemoteSportDetails = RemoteSportDetails()
)

data class RemoteSportDetails(
    val distanceMeters: Long = 0,
    val durationSeconds: Long = 0
)