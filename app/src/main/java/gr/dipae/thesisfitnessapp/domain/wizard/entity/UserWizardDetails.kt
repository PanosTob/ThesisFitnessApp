package gr.dipae.thesisfitnessapp.domain.wizard.entity

data class UserWizardDetails(
    val name: String,
    val fitnessLevel: String,
    val bodyWeightKg: Float?,
    val favoriteActivities: List<String>,
    val dietGoal: Map<String, Long?>,
    val dailyStepGoal: Long,
)