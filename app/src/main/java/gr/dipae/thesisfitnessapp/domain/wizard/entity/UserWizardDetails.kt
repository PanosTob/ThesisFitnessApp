package gr.dipae.thesisfitnessapp.domain.wizard.entity

import gr.dipae.thesisfitnessapp.domain.user.entity.DietGoal

data class UserWizardDetails(
    val name: String,
    val fitnessLevel: String,
    val favoriteActivitiesId: Array<String>,
    val dietGoal: DietGoal
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserWizardDetails

        if (name != other.name) return false
        if (fitnessLevel != other.fitnessLevel) return false
        if (!favoriteActivitiesId.contentEquals(other.favoriteActivitiesId)) return false
        if (dietGoal != other.dietGoal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + fitnessLevel.hashCode()
        result = 31 * result + favoriteActivitiesId.contentHashCode()
        result = 31 * result + dietGoal.hashCode()
        return result
    }
}