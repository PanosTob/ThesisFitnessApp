package gr.dipae.thesisfitnessapp.domain.user

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import gr.dipae.thesisfitnessapp.domain.user.entity.User

interface UserRepository {

    suspend fun isUserSignedIn(): Boolean

    suspend fun initializeGoogleSignIn(webClientId: String): BeginSignInResult

    suspend fun signInUser(googleSignInData: Intent): User?

    suspend fun registerUser()
}