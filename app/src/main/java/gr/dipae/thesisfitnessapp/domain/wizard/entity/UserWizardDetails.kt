package gr.dipae.thesisfitnessapp.domain.wizard.entity

data class UserWizardDetails(
    val name: String,
    val fitnessLevel: String,
    val favoriteActivities: List<String>,
    val dietGoal: Map<String, Long?>
)