package gr.dipae.thesisfitnessapp.domain.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.domain.user.entity.User

interface UserRepository {

    suspend fun isUserSignedIn(): Boolean
    suspend fun initializeGoogleSignIn(): IntentSender
    suspend fun signInUser(googleSignInData: Intent): User?
    suspend fun registerUser()
    fun getGoogleSignInDenialCount(): Int
    fun getGoogleSignInBlockedTime(): Long
    fun setGoogleSignInBlockedTime()
    fun setGoogleSignInDenialCount(count: Int)
    suspend fun resetGoogleSignInDenialCount()
}