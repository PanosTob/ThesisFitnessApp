package gr.dipae.thesisfitnessapp.usecase.login

import android.app.Activity
import android.content.Intent
import com.google.firebase.FirebaseException
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.domain.user.login.SignInResult
import gr.dipae.thesisfitnessapp.usecase.UseCase
import gr.dipae.thesisfitnessapp.usecase.user.GetUserDetailsUseCase
import gr.dipae.thesisfitnessapp.util.base.UserDeclinedException
import timber.log.Timber
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : UseCase {

    suspend operator fun invoke(googleSignInData: Intent?, resultCode: Int): SignInResult {
        try {
            if (googleSignInData == null) return SignInResult.Failure()
            if (resultCode != Activity.RESULT_OK) return SignInResult.Failure(exception = UserDeclinedException())

            repository.signInUser(googleSignInData)

            val existingUser = getUserDetailsUseCase()
            if (existingUser != null) {
                return SignInResult.AlreadyRegistered
            }

            repository.registerUser()
            return SignInResult.SuccessRegister
        } catch (ex: Exception) {
            Timber.tag(SignInUserUseCase::class.simpleName.toString()).e(ex)
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