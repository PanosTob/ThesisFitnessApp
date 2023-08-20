package gr.dipae.thesisfitnessapp.domain.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.domain.user.diet.entity.UserScannedFood
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkout
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkoutExercise
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun isUserSignedIn(): Boolean
    suspend fun initializeGoogleSignIn(): IntentSender
    suspend fun signInUser(googleSignInData: Intent)
    suspend fun getUser(): User?
    suspend fun getUserSportChallenges(): List<UserSportChallenge>
    suspend fun getUserSportChallengesBySportId(sportId: String): List<UserSportChallenge>
    suspend fun getUserDetailsLocally(): User?
    suspend fun logoutUser()
    suspend fun registerUser()
    fun getGoogleSignInDenialCount(): Int
    fun getGoogleSignInBlockedTime(): Long
    fun setGoogleSignInBlockedTime()
    fun setGoogleSignInDenialCount(count: Int)
    fun startAccelerometer()
    fun startMonitoringSteps()
    fun stopAccelerometer()
    fun stopMonitoringSteps()
    suspend fun getUserIsRunning(): Flow<Boolean>
    suspend fun getStepCounter(): Flow<Long>
    suspend fun resetGoogleSignInDenialCount()
    suspend fun setUserProfileDetails(userWizardDetails: UserWizardDetails)
    suspend fun getUserWorkouts(): List<UserWorkout>
    suspend fun getUserWorkoutExercises(workoutId: String): List<UserWorkoutExercise>
    suspend fun getDaySummary(): DaySummary?
    suspend fun setMacrosDaily(dailyDietRequest: DailyDietRequest, todaySummaryId: String?)
    suspend fun getDaySummarySportsDone(daySummaryId: String): List<SportDone>
    suspend fun getDaySummaryWorkoutsDone(daySummaryId: String): List<WorkoutDone>
    suspend fun getDaySummaryWorkoutExercisesDone(daySummaryId: String, workoutId: String): List<WorkoutExerciseDone>
    suspend fun getUserScannedFoods(): List<UserScannedFood>
    suspend fun getFavoriteSportIds(): List<String>
    suspend fun setFavoriteSportIds(favoritesSports: List<String>)
    suspend fun setUserNewSportChallenges(favoritesSports: List<String>)
    suspend fun setUserSportChallengeProgress(challengeId: String, progress: Double)
}