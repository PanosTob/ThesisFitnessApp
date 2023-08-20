package gr.dipae.thesisfitnessapp.data.user.mapper

import gr.dipae.thesisfitnessapp.data.Mapper
import gr.dipae.thesisfitnessapp.data.history.mapper.HistoryMapper
import gr.dipae.thesisfitnessapp.data.history.model.RemoteDaySummary
import gr.dipae.thesisfitnessapp.data.history.model.RemoteSportDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutExerciseDone
import gr.dipae.thesisfitnessapp.data.user.diet.model.RemoteUserScannedFood
import gr.dipae.thesisfitnessapp.data.user.model.RemoteDietGoal
import gr.dipae.thesisfitnessapp.data.user.model.RemoteUser
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkout
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkoutExercise
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import gr.dipae.thesisfitnessapp.domain.user.diet.entity.UserScannedFood
import gr.dipae.thesisfitnessapp.domain.user.entity.DietGoal
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkout
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkoutExercise
import javax.inject.Inject

class UserMapper @Inject constructor(
    private val historyMapper: HistoryMapper
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
            workouts = emptyList()
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

    fun mapDaySummary(daySummary: RemoteDaySummary?): DaySummary? {
        return historyMapper(daySummary)
    }

    @JvmName(name = "5")
    operator fun invoke(userScannedFoods: List<RemoteUserScannedFood>): List<UserScannedFood> {
        return userScannedFoods.map { it.toUserScanneFood() }
    }

    @JvmName(name = "6")
    operator fun invoke(sportsDone: List<RemoteSportDone>): List<SportDone> {
        return sportsDone.map { it.toSportDone() }
    }

    @JvmName(name = "7")
    operator fun invoke(workoutsDone: List<RemoteWorkoutDone>): List<WorkoutDone> {
        return workoutsDone.map { it.toWorkoutDone() }
    }

    @JvmName(name = "8")
    operator fun invoke(workoutExercisesDone: List<RemoteWorkoutExerciseDone>): List<WorkoutExerciseDone> {
        return workoutExercisesDone.map { it.toWorkoutExerciseDone() }
    }

    private fun mapDietGoal(remoteDietGoal: RemoteDietGoal): DietGoal {
        return DietGoal(
            calories = remoteDietGoal.calories,
            carbohydrates = remoteDietGoal.carbohydrates ?: 0.0,
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

    /*private fun mapUserSportChallenges(remoteUserSportChallenges: List<RemoteUserSportChallenge>?): List<UserSportChallenge>? {
        remoteUserSportChallenges ?: return null
        return remoteUserSportChallenges.map {
            UserSportChallenge(
                sportId = it.activityId,
                sportName = it.activityName,
                goal = it.,
                completed: Boolean
            )
        }
    }*/
}
