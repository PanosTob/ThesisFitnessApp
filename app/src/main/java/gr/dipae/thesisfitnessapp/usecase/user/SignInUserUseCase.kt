package gr.dipae.thesisfitnessapp.usecase.user

import android.content.Intent
import com.google.firebase.FirebaseException
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.login.SignInResult
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(googleSignInData: Intent?): SignInResult {
        try {
            googleSignInData ?: return SignInResult.Failure()

            val existingUser = repository.signInUser(googleSignInData)
            if (existingUser == null) {
                repository.registerUser()
            }
            return SignInResult.Success
        } catch (ex: Exception) {
            Timber.tag(SignInUserUseCase::class.simpleName).e(ex)
            return handleException(ex)
        }
    }

    private fun handleException(exception: Exception): SignInResult {
        return when (exception) {
            is FirebaseException -> SignInResult.FirebaseFailure(exception)
            else -> SignInResult.Failure()
        }
    }
}