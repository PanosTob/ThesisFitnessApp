package gr.dipae.thesisfitnessapp.domain.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails

interface UserRepository {

    suspend fun isUserSignedIn(): Boolean
    suspend fun initializeGoogleSignIn(): IntentSender
    suspend fun signInUser(googleSignInData: Intent): User?
    suspend fun logoutUser()
    suspend fun registerUser()
    fun getGoogleSignInDenialCount(): Int
    fun getGoogleSignInBlockedTime(): Long
    fun setGoogleSignInBlockedTime()
    fun setGoogleSignInDenialCount(count: Int)
    suspend fun resetGoogleSignInDenialCount()
    suspend fun getUserWizardDetails(): UserWizardDetails?
    suspend fun setUserWizardDetails(userWizardDetails: UserWizardDetails)
    suspend fun setUserFitnessProfile(
        userName: String,
        fitnessLevel: FitnessLevel,
        favoriteSports: List<String>,
        calories: String,
        carbs: String,
        fats: String,
        proteins: String,
        waterML: String
    )
}