package gr.dipae.thesisfitnessapp.domain.wizard.entity

data class UserWizardDetails(
    val name: String,
    val fitnessLevel: String?,
    val bodyWeight: Double?,
    val muscleMassPercent: Double?,
    val bodyFatPercent: Double?,
    val favoriteActivitiesIds: List<String>,
    val dietGoal: Map<String, Long?>,
    val dailyStepsGoal: Long,
    val dailyCaloricBurnGoal: Long
)