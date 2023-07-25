package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.history.model.RemoteDaySummary
import gr.dipae.thesisfitnessapp.data.history.model.RemoteSportDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutDone
import gr.dipae.thesisfitnessapp.data.history.model.RemoteWorkoutExerciseDone
import gr.dipae.thesisfitnessapp.data.user.diet.model.RemoteUserScannedFood
import gr.dipae.thesisfitnessapp.data.user.model.RemoteUser
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkout
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkoutExercise
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails

interface UserDataSource {
    suspend fun isUserSignedIn(): Boolean
    suspend fun initializeGoogleSignIn(): IntentSender
    suspend fun signInUser(googleSignInData: Intent)
    suspend fun getUser(): RemoteUser?
    suspend fun getUserDetailsLocally(): RemoteUser?
    suspend fun logoutUser()
    suspend fun registerUser()
    fun getUserDeclinedSignInCount(): Int
    fun getGoogleSignInBlockedTime(): Long
    fun setGoogleSignInBlockedTime()
    fun setGoogleSignInDenialCount(count: Int)
    suspend fun resetGoogleSignInDenialCount()
    suspend fun getUserWizardDetails(): UserWizardDetails?
    suspend fun setUserWizardDetails(wizardDetails: UserWizardDetails)
    suspend fun setUserFitnessProfile(wizardDetails: UserWizardDetails)
    suspend fun getUserWorkouts(): List<RemoteUserWorkout>
    suspend fun getUserWorkoutExercises(workoutId: String): List<RemoteUserWorkoutExercise>
    suspend fun getUserScannedFoods(): List<RemoteUserScannedFood>
    suspend fun getDaySummary(): RemoteDaySummary?
    suspend fun getDaySummarySportsDone(daySummaryId: String): List<RemoteSportDone>
    suspend fun getDaySummaryWorkoutsDone(daySummaryId: String): List<RemoteWorkoutDone>
    suspend fun getDaySummaryWorkoutExercisesDone(daySummaryId: String, workoutId: String): List<RemoteWorkoutExerciseDone>
    suspend fun getFavoriteSportIds(): List<String>
}