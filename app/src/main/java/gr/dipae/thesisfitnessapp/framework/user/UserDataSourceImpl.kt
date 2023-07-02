package gr.dipae.thesisfitnessapp.framework.user

import android.content.Intent
import android.content.SharedPreferences
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.data.user.UserDataSource
import gr.dipae.thesisfitnessapp.data.user.login.model.RemoteUser
import gr.dipae.thesisfitnessapp.di.module.qualifier.GeneralSharedPrefs
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import gr.dipae.thesisfitnessapp.util.USER_EMAIL
import gr.dipae.thesisfitnessapp.util.USER_NAME
import gr.dipae.thesisfitnessapp.util.base.GoogleAuthenticationException
import gr.dipae.thesisfitnessapp.util.ext.documentToResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth,
//    private val db: AppDatabase,
    @GeneralSharedPrefs private val sharedPrefs: SharedPreferences,
    private val fireStore: FirebaseFirestore
) : UserDataSource {
    override suspend fun initializeGoogleSignIn(webClientId: String): BeginSignInResult {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Google Console Auth 2.0 Web client key.
                    .setServerClientId(webClientId)
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
        return oneTapClient.beginSignIn(signInRequest).await()
    }

    private fun getGoogleUserId(googleSignInData: Intent): String {
        val credential = oneTapClient.getSignInCredentialFromIntent(googleSignInData)
        return credential.googleIdToken ?: throw GoogleAuthenticationException()
    }

    override suspend fun isUserSignedIn(): Boolean = auth.currentUser != null

    private fun getFirebaseUserId() = auth.currentUser?.uid ?: throw FirebaseNoSignedInUserException("")

    override suspend fun registerUser() {
        val firebaseUserId = getFirebaseUserId()

        fireStore.collection(USERS_COLLECTION).document(firebaseUserId)
            .set(
                hashMapOf(
                    USER_EMAIL to auth.currentUser?.email,
                    USER_NAME to auth.currentUser?.displayName
                )
            ).await()
    }

    override suspend fun signInUser(googleSignInData: Intent): RemoteUser? {
        val firebaseCredential = GoogleAuthProvider.getCredential(getGoogleUserId(googleSignInData), null)

        auth.signInWithCredential(firebaseCredential).await()
        val firebaseUserId = getFirebaseUserId()

        return fireStore.collection(USERS_COLLECTION).document(firebaseUserId).get().documentToResponse<RemoteUser>()
    }

    override suspend fun logout() {
        auth.signOut()
    }
}