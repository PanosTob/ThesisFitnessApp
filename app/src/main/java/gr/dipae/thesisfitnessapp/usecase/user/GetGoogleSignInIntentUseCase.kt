package gr.dipae.thesisfitnessapp.usecase.user

import com.google.android.gms.auth.api.identity.BeginSignInResult
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetGoogleSignInIntentUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(webClientId: String): BeginSignInResult? {
        return try {
            repository.initializeGoogleSignIn(webClientId)
        } catch (e: Exception) {
            Timber.tag(GetGoogleSignInIntentUseCase::class.simpleName).e(e)
            null
        }
    }
}