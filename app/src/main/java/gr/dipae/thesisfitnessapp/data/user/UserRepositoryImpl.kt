package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.firestore.DocumentSnapshot
import gr.dipae.thesisfitnessapp.data.user.login.mapper.UserAuthenticationMapper
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val userAuthenticationMapper: UserAuthenticationMapper
) : UserRepository {
    override suspend fun isUserSignedIn(): Boolean {
        return dataSource.isUserSignedIn()
    }

    override suspend fun initializeGoogleSignIn(webClientId: String): BeginSignInResult {
        return dataSource.initializeGoogleSignIn(webClientId)
    }

    override suspend fun signInUser(googleSignInData: Intent): DocumentSnapshot? {
        return dataSource.signInUser(googleSignInData)
    }

    override suspend fun registerUser() {
        dataSource.registerUser()
    }
}