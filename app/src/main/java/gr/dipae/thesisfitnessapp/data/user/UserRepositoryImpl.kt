package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.diet.model.DailyDietRequest
import gr.dipae.thesisfitnessapp.data.history.mapper.HistoryMapper
import gr.dipae.thesisfitnessapp.data.user.mapper.UserMapper
import gr.dipae.thesisfitnessapp.domain.history.entity.DaySummary
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.challenges.entity.UserSportChallenge
import gr.dipae.thesisfitnessapp.domain.user.diet.entity.UserScannedFood
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkout
import gr.dipae.thesisfitnessapp.domain.user.workout.entity.UserWorkoutExercise
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val userMapper: UserMapper,
    private val historyMapper: HistoryMapper,
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

    override suspend fun getUserSportChallenges(): List<UserSportChallenge> {
//        return sportChallengesMapper(dataSource.getUserSportChallenges())
        return listOf()
    }

    override suspend fun getUserSportChallengesBySportId(sportId: String): List<UserSportChallenge> {
        return userMapper.mapUserSportChallenges(dataSource.getUserSportChallengesBySportId(sportId))
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

    override fun startMonitoringSteps() {
        return dataSource.startMonitoringSteps()
    }

    override fun stopAccelerometer() {
        return dataSource.stopAccelerometer()
    }

    override fun stopMonitoringSteps() {
        return dataSource.stopMonitoringSteps()
    }

    override fun setGoogleSignInDenialCount(count: Int) {
        return dataSource.setGoogleSignInDenialCount(count)
    }

    override suspend fun getUserIsRunning(): Flow<Boolean> {
        return dataSource.getUserIsRunning()
    }

    override fun startAccelerometer() {
        return dataSource.startAccelerometer()
    }

    override suspend fun resetGoogleSignInDenialCount() {
        dataSource.resetGoogleSignInDenialCount()
    }

    override suspend fun setUserProfileDetails(userWizardDetails: UserWizardDetails) {
        dataSource.setUserProfileDetails(userWizardDetails)
    }

    override suspend fun getUserWorkouts(): List<UserWorkout> {
        return userMapper(dataSource.getUserWorkouts())
    }

    override suspend fun getUserWorkoutExercises(workoutId: String): List<UserWorkoutExercise> {
        return userMapper(dataSource.getUserWorkoutExercises(workoutId))
    }

    override suspend fun getDaySummary(): DaySummary? {
        return historyMapper.mapDaySummary(dataSource.getDaySummary())
    }

    override suspend fun getDaySummariesByRange(startDate: Long, endDate: Long): List<DaySummary> {
        return historyMapper(dataSource.getDaySummariesByRange(startDate, endDate))
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

    override suspend fun setUserNewSportChallenges(favoritesSports: List<String>) {
        dataSource.setUserNewSportChallenges(favoritesSports)
    }

    override suspend fun setUserSportChallengeProgress(challengeId: String, progress: Long) {
        dataSource.setUserSportChallengeProgress(challengeId, progress)
    }

    override suspend fun setMacrosDaily(dailyDietRequest: DailyDietRequest, todaySummaryId: String?) {
        dataSource.setMacrosDaily(dailyDietRequest, todaySummaryId)
    }
}