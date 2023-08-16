package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.data.user.mapper.UserMapper
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.history.entity.SportDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutDone
import gr.dipae.thesisfitnessapp.domain.history.entity.WorkoutExerciseDone
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.diet.entity.UserScannedFood
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkout
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkoutExercise
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val userMapper: UserMapper
) : UserRepository {
    override suspend fun isUserSignedIn(): Boolean {
        return dataSource.isUserSignedIn()
    }

    override suspend fun initializeGoogleSignIn(): IntentSender {
        return dataSource.initializeGoogleSignIn()
    }

    override suspend fun signInUser(googleSignInData: Intent) {
        return dataSource.signInUser(googleSignInData)
    }

    override suspend fun getUser(): User? {
        return userMapper(dataSource.getUser())
    }

    override suspend fun getUserDetailsLocally(): User? {
        return userMapper(dataSource.getUserDetailsLocally())
    }

    override suspend fun logoutUser() {
        dataSource.logoutUser()
    }

    override suspend fun registerUser() {
        dataSource.registerUser()
    }

    override fun getGoogleSignInDenialCount(): Int {
        return dataSource.getUserDeclinedSignInCount()
    }

    override fun getGoogleSignInBlockedTime(): Long {
        return dataSource.getGoogleSignInBlockedTime()
    }

    override fun setGoogleSignInBlockedTime() {
        return dataSource.setGoogleSignInBlockedTime()
    }

    override suspend fun getStepCounter(): Flow<Long> {
        return dataSource.getStepCounter()
    }

    override suspend fun updateStepCounterValue(stepCounterValue: Long) {
        return dataSource.updateStepCounterValue(stepCounterValue)
    }

    override fun setGoogleSignInDenialCount(count: Int) {
        return dataSource.setGoogleSignInDenialCount(count)
    }

    override suspend fun getUserIsRunning(): Flow<Boolean> {
        return dataSource.getUserIsRunning()
    }

    override suspend fun resetGoogleSignInDenialCount() {
        dataSource.resetGoogleSignInDenialCount()
    }

    override suspend fun getUserWizardDetails(): UserWizardDetails? {
        return dataSource.getUserWizardDetails()
    }

    override suspend fun setUserWizardDetails(userWizardDetails: UserWizardDetails) {
        dataSource.setUserWizardDetails(userWizardDetails)
    }

    override suspend fun setUserFitnessProfile(userWizardDetails: UserWizardDetails) {
        dataSource.setUserFitnessProfile(userWizardDetails)
    }

    override suspend fun getUserWorkouts(): List<UserWorkout> {
        return userMapper(dataSource.getUserWorkouts())
    }

    override suspend fun getUserWorkoutExercises(workoutId: String): List<UserWorkoutExercise> {
        return userMapper(dataSource.getUserWorkoutExercises(workoutId))
    }

    override suspend fun getDaySummary(): DaySummary? {
        return userMapper.mapDaySummary(dataSource.getDaySummary())
    }

    override suspend fun getDaySummarySportsDone(daySummaryId: String): List<SportDone> {
        return userMapper(dataSource.getDaySummarySportsDone(daySummaryId))
    }

    override suspend fun getDaySummaryWorkoutsDone(daySummaryId: String): List<WorkoutDone> {
        return userMapper(dataSource.getDaySummaryWorkoutsDone(daySummaryId))
    }

    override suspend fun getDaySummaryWorkoutExercisesDone(daySummaryId: String, workoutId: String): List<WorkoutExerciseDone> {
        return userMapper(dataSource.getDaySummaryWorkoutExercisesDone(daySummaryId, workoutId))
    }

    override suspend fun getUserScannedFoods(): List<UserScannedFood> {
        return userMapper(dataSource.getUserScannedFoods())
    }

    override suspend fun getFavoriteSportIds(): List<String> {
        return dataSource.getFavoriteSportIds()
    }

    override suspend fun setFavoriteSportIds(favoritesSports: List<String>) {
        dataSource.setFavoriteSportIds(favoritesSports)
    }

    override suspend fun setMacrosDaily(dailyDietRequest: DailyDietRequest, todaySummaryId: String?) {
        dataSource.setMacrosDaily(dailyDietRequest, todaySummaryId)
    }
}