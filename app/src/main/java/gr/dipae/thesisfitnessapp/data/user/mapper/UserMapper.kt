package gr.dipae.thesisfitnessapp.data.user.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.user.model.RemoteDietGoal
import gr.dipae.thesisfitnessapp.data.user.model.RemoteUser
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
            carbohydrates = remoteDietGoal.carbonhydrateGrams ?: 0.0,
            fats = remoteDietGoal.fatGrams ?: 0.0,
            protein = remoteDietGoal.proteinGrams ?: 0.0,
            waterML = remoteDietGoal.waterML ?: 0.0
        )
    }

    private fun mapFitnessLevel(fitnessLevel: String): FitnessLevel {
        return when (fitnessLevel) {
            "beginner", "BEGINNER" -> FitnessLevel.Beginner
            "intermediate", "INTERMEDIATE" -> FitnessLevel.Intermediate
            "advanced", "ADVANCED" -> FitnessLevel.Advanced
            else -> FitnessLevel.Unknown
        }
    }
}
