package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.user.login.mapper.UserMapper
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val userMapper: UserMapper
) : UserRepository {
    override suspend fun isUserSignedIn(): Boolean {
        return dataSource.isUserSignedIn()
    }

    override suspend fun initializeGoogleSignIn(): IntentSender {
        return dataSource.initializeGoogleSignIn()
    }

    override suspend fun signInUser(googleSignInData: Intent): User? {
        return userMapper(dataSource.signInUser(googleSignInData))
    }

    override suspend fun registerUser() {
        dataSource.registerUser()
    }

    override fun getGoogleSignInDenialCount(): Int {
        return dataSource.getUserDeclinedSignInCount()
    }

    override fun getGoogleSignInBlockedTime(): Long {
        return dataSource.getGoogleSignInBlockedTime()
    }

    override fun setGoogleSignInBlockedTime() {
        return dataSource.setGoogleSignInBlockedTime()
    }

    override fun setGoogleSignInDenialCount(count: Int) {
        return dataSource.setGoogleSignInDenialCount(count)
    }

    override suspend fun resetGoogleSignInDenialCount() {
        dataSource.resetGoogleSignInDenialCount()
    }
}