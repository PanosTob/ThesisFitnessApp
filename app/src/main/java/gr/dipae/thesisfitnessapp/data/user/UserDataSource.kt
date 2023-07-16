package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.user.model.RemoteUser
import gr.dipae.thesisfitnessapp.data.user.workout.model.RemoteUserWorkout
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails

interface UserDataSource {
    suspend fun isUserSignedIn(): Boolean
    suspend fun initializeGoogleSignIn(): IntentSender
    suspend fun signInUser(googleSignInData: Intent)
    suspend fun getUser(): RemoteUser?
    suspend fun getUserDetailsLocally(): RemoteUser?
    suspend fun logoutUser()
    suspend fun registerUser()
    suspend fun logout()
    fun getUserDeclinedSignInCount(): Int
    fun getGoogleSignInBlockedTime(): Long
    fun setGoogleSignInBlockedTime()
    fun setGoogleSignInDenialCount(count: Int)
    suspend fun resetGoogleSignInDenialCount()
    suspend fun getUserWizardDetails(): UserWizardDetails?
    suspend fun setUserWizardDetails(wizardDetails: UserWizardDetails)
    suspend fun setUserFitnessProfile(wizardDetails: UserWizardDetails)
    suspend fun getUserWorkouts(): List<RemoteUserWorkout>
}