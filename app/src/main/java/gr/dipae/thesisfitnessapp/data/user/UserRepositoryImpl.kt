package gr.dipae.thesisfitnessapp.data.user

import android.content.Intent
import android.content.IntentSender
import gr.dipae.thesisfitnessapp.data.user.login.mapper.UserMapper
import gr.dipae.thesisfitnessapp.data.wizard.WizardMapper
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.entity.FitnessLevel
import gr.dipae.thesisfitnessapp.domain.user.entity.User
import gr.dipae.thesisfitnessapp.domain.wizard.entity.UserWizardDetails
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val userMapper: UserMapper,
    private val wizardMapper: WizardMapper
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

    override suspend fun logoutUser() {
        dataSource.logoutUser()
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

    override suspend fun getUserWizardDetails(): UserWizardDetails? {
        return dataSource.getUserWizardDetails()
    }

    override suspend fun setUserWizardDetails(userWizardDetails: UserWizardDetails) {
        dataSource.setUserWizardDetails(userWizardDetails)
    }

    override suspend fun setUserFitnessProfile(
        userName: String,
        fitnessLevel: FitnessLevel,
        favoriteSports: List<String>,
        calories: String,
        carbs: String,
        fats: String,
        proteins: String,
        waterML: String
    ) {

        dataSource.setUserFitnessProfile(
            wizardMapper(
                userName,
                fitnessLevel,
                favoriteSports,
                calories,
                carbs,
                fats,
                proteins,
                waterML
            )
        )
    }
}