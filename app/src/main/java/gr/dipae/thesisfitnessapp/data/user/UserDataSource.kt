package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.firestore.DocumentSnapshot

interface UserDataSource {

    suspend fun isUserSignedIn(): Boolean

    suspend fun initializeGoogleSignIn(webClientId: String): BeginSignInResult

    suspend fun signInUser(googleSignInData: Intent): DocumentSnapshot?

    suspend fun registerUser()

    suspend fun logout()
}