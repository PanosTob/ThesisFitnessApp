package gr.dipae.thesisfitnessapp.data.user.login.model

data class RemoteUser(
    val name: String = "unknown name",
    val email: String = "unknown email",
    val fitnessLevel: String = "",
    val favoriteActivities: List<String> = emptyList(),
    val dietGoal: RemoteDietGoal = RemoteDietGoal()
)

data class RemoteDietGoal(
    val carbonhydrateGrams: Double? = 0.0,
    val fatGrams: Double? = 0.0,
    val proteinGrams: Double? = 0.0,
    val waterML: Double? = 0.0
)