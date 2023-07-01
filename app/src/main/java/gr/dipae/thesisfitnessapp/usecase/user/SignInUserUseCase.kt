package gr.dipae.thesisfitnessapp.usecase.user

import android.content.Intent
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.login.SignInResult
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(googleSignInData: Intent?): SignInResult {
        try {
            googleSignInData ?: return SignInResult.Failure

            val existingUser = repository.signInUser(googleSignInData)
            if (existingUser == null) {
                repository.registerUser()
            }
            return SignInResult.Success
        } catch (e: Exception) {
            return SignInResult.Failure
        }
    }
}