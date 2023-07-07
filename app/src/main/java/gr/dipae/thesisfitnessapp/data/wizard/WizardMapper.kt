package gr.dipae.thesisfitnessapp.data.wizard

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_CALORIES
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_CARBOHYDRATES
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_FATS
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_PROTEIN
import gr.dipae.thesisfitnessapp.util.USER_DIET_GOAL_WATER
import javax.inject.Inject

class WizardMapper @Inject constructor() : Mapper {

    operator fun invoke(
        userName: String,
        fitnessLevel: FitnessLevel,
        favoriteSports: List<String>,
        calories: String,
        carbs: String,
        fats: String,
        proteins: String,
        waterML: String
    ): UserWizardDetails {
        return UserWizardDetails(
            name = userName,
            fitnessLevel = fitnessLevel.name,
            favoriteActivities = favoriteSports.map { "/activities/$it" },
            dietGoal = mapOf(
                USER_DIET_GOAL_CARBOHYDRATES to carbs.toLongOrNull(),
                USER_DIET_GOAL_FATS to fats.toLongOrNull(),
                USER_DIET_GOAL_PROTEIN to proteins.toLongOrNull(),
                USER_DIET_GOAL_CALORIES to calories.toLongOrNull(),
                USER_DIET_GOAL_WATER to waterML.toLongOrNull()
            )
        )
    }
}