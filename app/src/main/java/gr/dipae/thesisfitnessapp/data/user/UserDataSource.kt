package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.user.login.model.RemoteUser

interface UserDataSource {
    suspend fun isUserSignedIn(): Boolean
    suspend fun initializeGoogleSignIn(): IntentSender
    suspend fun signInUser(googleSignInData: Intent): RemoteUser?
    suspend fun registerUser()
    suspend fun logout()
    fun getUserDeclinedSignInCount(): Int
    fun getGoogleSignInBlockedTime(): Long
    fun setGoogleSignInBlockedTime()
    fun setGoogleSignInDenialCount(count: Int)
    suspend fun resetGoogleSignInDenialCount()
}