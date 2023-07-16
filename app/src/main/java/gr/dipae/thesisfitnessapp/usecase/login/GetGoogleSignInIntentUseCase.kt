package gr.dipae.thesisfitnessapp.usecase.login

import android.content.IntentSender
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetGoogleSignInIntentUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(): IntentSender? {
        return try {
            repository.initializeGoogleSignIn()
        } catch (e: Exception) {
            Timber.tag(GetGoogleSignInIntentUseCase::class.simpleName.toString()).e(e)
            null
        }
    }
}