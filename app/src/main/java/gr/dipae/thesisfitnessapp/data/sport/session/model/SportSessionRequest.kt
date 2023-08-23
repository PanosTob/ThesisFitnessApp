package gr.dipae.thesisfitnessapp.data.sport.session.model

data class SportSessionRequest(
    val activityId: String,
    val breakTime: Long,
    val goalParameter: SportParameterRequest?
)

data class SportParameterRequest(
    val name: String,
    val value: Any
)