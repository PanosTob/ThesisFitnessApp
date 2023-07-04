package gr.dipae.thesisfitnessapp.data.user.login.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.user.login.model.RemoteDietGoal
import gr.dipae.thesisfitnessapp.data.user.login.model.RemoteUser
import gr.dipae.thesisfitnessapp.domain.user.entity.DietGoal
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper {

    operator fun invoke(remoteUser: RemoteUser?): User? {
        remoteUser ?: return null
        return User(
            name = remoteUser.name,
            email = remoteUser.email,
            fitnessLevel = mapFitnessLevel(remoteUser.fitnessLevel),
            dietGoal = mapDietGoal(remoteUser.dietGoal),
            favoriteActivities = remoteUser.favoriteActivities,
            daySummaries = emptyList(),
            scannedFoods = emptyList(),
            workouts = emptyList()
        )
    }

    private fun mapDietGoal(remoteDietGoal: RemoteDietGoal): DietGoal {
        return DietGoal(
            carbohydrates = remoteDietGoal.carbonhydrateGrams,
            fats = remoteDietGoal.fatGrams,
            protein = remoteDietGoal.proteinGrams,
            waterML = remoteDietGoal.waterML
        )
    }

    private fun mapFitnessLevel(fitnessLevel: String): FitnessLevel {
        return when (fitnessLevel) {
            "beginner", "BEGINNER" -> FitnessLevel.BEGINNER
            "intermediate", "INTERMEDIATE" -> FitnessLevel.INTERMEDIATE
            "advanced", "ADVANCED" -> FitnessLevel.ADVANCED
            else -> FitnessLevel.UNKNOWN
        }
    }
}
