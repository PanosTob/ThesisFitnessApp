package gr.dipae.thesisfitnessapp.framework.user

import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import gr.dipae.thesisfitnessapp.data.user.UserDataSource
import gr.dipae.thesisfitnessapp.data.user.login.broadcast.LoginBroadcast
import gr.dipae.thesisfitnessapp.data.user.login.model.RemoteUser
import gr.dipae.thesisfitnessapp.di.module.qualifier.GeneralSharedPrefs
import gr.dipae.thesisfitnessapp.util.GOOGLE_SIGN_IN_BLOCKED_TIME
import gr.dipae.thesisfitnessapp.util.USERS_COLLECTION
import gr.dipae.thesisfitnessapp.util.USER_DECLINED_SIGN_IN_COUNTER
import gr.dipae.thesisfitnessapp.util.USER_EMAIL
import gr.dipae.thesisfitnessapp.util.USER_NAME
import gr.dipae.thesisfitnessapp.util.base.GoogleAuthenticationException
import gr.dipae.thesisfitnessapp.util.ext.get
import gr.dipae.thesisfitnessapp.util.ext.getDocumentResponse
import gr.dipae.thesisfitnessapp.util.ext.set
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val googleSignInRequest: BeginSignInRequest,
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth,
//    private val db: AppDatabase,
    @GeneralSharedPrefs private val sharedPrefs: SharedPreferences,
    private val fireStore: FirebaseFirestore,
    private val loginBroadcast: LoginBroadcast
) : UserDataSource {
    override suspend fun initializeGoogleSignIn(): IntentSender {
        return oneTapClient.beginSignIn(googleSignInRequest).await().pendingIntent.intentSender
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

        return fireStore.collection(USERS_COLLECTION).document(firebaseUserId).getDocumentResponse<RemoteUser>()
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override fun getUserDeclinedSignInCount(): Int {
        return sharedPrefs[USER_DECLINED_SIGN_IN_COUNTER, 0] ?: 0
    }

    override fun setGoogleSignInDenialCount(count: Int) {
        sharedPrefs[USER_DECLINED_SIGN_IN_COUNTER] = count
    }

    override suspend fun resetGoogleSignInDenialCount() {
        sharedPrefs[USER_DECLINED_SIGN_IN_COUNTER] = 0
        loginBroadcast.refreshGoogleSignInEnabledState(true)
    }

    override fun setGoogleSignInBlockedTime() {
        sharedPrefs[GOOGLE_SIGN_IN_BLOCKED_TIME] = Calendar.getInstance().timeInMillis
    }

    override fun getGoogleSignInBlockedTime(): Long {
        return sharedPrefs[GOOGLE_SIGN_IN_BLOCKED_TIME, 0] ?: 0
    }
}