package gr.dipae.thesisfitnessapp.data.user.mapper

import gr.dipae.thesisfitnessapp.BuildConfig
import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.history.mapper.HistoryMapper
import gr.dipae.thesisfitnessapp.data.sport.mapper.SportsMapper
import gr.dipae.thesisfitnessapp.data.user.diet.model.RemoteUserScannedFood
import gr.dipae.thesisfitnessapp.data.user.model.RemoteDietGoal
import gr.dipae.thesisfitnessapp.data.user.model.RemoteUser
import gr.dipae.thesisfitnessapp.data.user.model.RemoteUserSportChallenge
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkout
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkoutExercise
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.SportChallenge
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.domain.user.diet.entity.UserScannedFood
import gr.dipae.thesisfitnessapp.domain.user.entity.DietGoal
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkout
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkoutExercise
import javax.inject.Inject

class UserMapper @Inject constructor(
    private val historyMapper: HistoryMapper,
    private val sportsMapper: SportsMapper
) : Mapper {

    @JvmName(name = "1")
    operator fun invoke(remoteUser: RemoteUser?): User? {
        remoteUser ?: return null
        return User(
            name = remoteUser.name,
            email = remoteUser.email,
            imgUrl = remoteUser.imgUrl,
            bodyWeight = remoteUser.bodyWeight,
            bodyFatPercent = remoteUser.bodyFatPercent,
            muscleMassPercent = remoteUser.muscleMassPercent,
            fitnessLevel = mapFitnessLevel(remoteUser.fitnessLevel),
            dailyStepsGoal = remoteUser.dailyStepsGoal,
            dailyCaloricBurnGoal = remoteUser.dailyCaloricBurnGoal,
            dietGoal = mapDietGoal(remoteUser.dietGoal),
            favoriteActivities = remoteUser.favoriteActivitiesIds,
            daySummaries = emptyList(),
            scannedFoods = emptyList(),
            workouts = emptyList(),
            sportChallenges = mapUserSportChallenges(remoteUser.challenges)
        )
    }

    @JvmName(name = "2")
    operator fun invoke(userWorkouts: List<RemoteUserWorkout>): List<UserWorkout> {
        return userWorkouts.map { it.toUserWorkout() }
    }

    @JvmName(name = "3")
    operator fun invoke(userWorkoutExercises: List<RemoteUserWorkoutExercise>): List<UserWorkoutExercise> {
        return userWorkoutExercises.map { it.toUserWorkoutExercise() }
    }

    @JvmName(name = "5")
    operator fun invoke(userScannedFoods: List<RemoteUserScannedFood>): List<UserScannedFood> {
        return userScannedFoods.map { it.toUserScanneFood() }
    }

    private fun mapDietGoal(remoteDietGoal: RemoteDietGoal): DietGoal {
        return DietGoal(
            calories = remoteDietGoal.calories,
            carbohydrates = remoteDietGoal.carbohydrates,
            fats = remoteDietGoal.fats,
            protein = remoteDietGoal.protein,
            waterML = remoteDietGoal.water
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

    fun mapUserSportChallenges(remoteUserSportChallenges: List<RemoteUserSportChallenge>?): List<UserSportChallenge> {
        remoteUserSportChallenges ?: return emptyList()

        return remoteUserSportChallenges.map {
            UserSportChallenge(
                id = it.challengeId,
                sportId = it.activityId,
                sportName = it.activityName,
                sportImgUrl = "${BuildConfig.GOOGLE_STORAGE_FIREBASE}${it.activityImgUrl}${BuildConfig.GOOGLE_STORAGE_FIREBASE_QUERY_PARAMS}",
                goal = SportChallenge(
                    type = sportsMapper.mapSportParameterType(it.goal.type),
                    name = it.goal.type,
                    value = it.goal.value
                ),
                progress = it.progress
            )
        }
    }
}
