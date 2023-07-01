package gr.dipae.thesisfitnessapp.usecase.user

import com.google.android.gms.auth.api.identity.BeginSignInResult
import gr.dipae.thesisfitnessapp.domain.user.UserRepository
import gr.dipae.thesisfitnessapp.usecase.UseCase
import javax.inject.Inject

class GetGoogleSignInIntentUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase {

    suspend operator fun invoke(webClientId: String): BeginSignInResult {
        return repository.initializeGoogleSignIn(webClientId)
    }
}