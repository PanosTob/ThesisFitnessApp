package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import gr.dipae.thesisfitnessapp.data.user.login.model.RemoteUser

interface UserDataSource {

    suspend fun isUserSignedIn(): Boolean

    suspend fun initializeGoogleSignIn(webClientId: String): BeginSignInResult

    suspend fun signInUser(googleSignInData: Intent): RemoteUser?

    suspend fun registerUser()

    suspend fun logout()
}